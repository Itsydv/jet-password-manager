package io.itsydv.jetpasswordmanager.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.itsydv.jetpasswordmanager.R
import io.itsydv.jetpasswordmanager.components.MyInputText
import io.itsydv.jetpasswordmanager.model.Credential
import io.itsydv.jetpasswordmanager.navigation.Screens
import io.itsydv.jetpasswordmanager.ui.theme.*
import java.util.*

@OptIn(ExperimentalTextApi::class)
@Composable
fun ListCredentialsScreen(navController: NavHostController, viewModel: MainViewModel) {
    val gradientColors = listOf(Color(0xFF03A9F4), Color(0xFF673AB7), Color(0xFFFF5722))
    var searchQuery by remember { mutableStateOf("") }
    var credentials by remember {
        mutableStateOf<List<Credential>>(emptyList())
    }
    credentials = viewModel.credentials.collectAsStateWithLifecycle().value.sortedBy { it.website }
    var filteredVaults: List<Credential>
    val filters = listOf("All", "Recent", "Favourite", "Last Edit")
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.app_name),
                        style = TextStyle(
                            brush = Brush.linearGradient(colors = gradientColors),
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            textAlign = TextAlign.Center
                        )
                    )
                },
                backgroundColor = if (isSystemInDarkTheme()) DarkGray else Color.White  ,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.Add.route) },
                backgroundColor = CoolBlue,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add,"")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MyInputText(
                text = searchQuery,
                placeHolder = "Search your vaults",
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search") },
                onTextChange = { searchQuery = it },
                height = 56.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .wrapContentWidth()
            )

            if (searchQuery.isEmpty()) {
                LazyRow(modifier = Modifier.padding(bottom = 16.dp)) {
                    itemsIndexed(filters) { index, filter ->
                        DataFilters(label = filter, selected = (index == selectedIndex)) { label ->
                            selectedIndex = index
                            when (label) {
                                "All" -> credentials = credentials.sortedBy { it.website }
                                "Recent" -> credentials =
                                    credentials.sortedByDescending { it.dateCreated }
                                "Favourite" -> credentials = credentials.sortedWith(compareBy(
                                    { !it.favourite },
                                    { it.dateCreated }
                                ))
                                "Last Edit" -> credentials =
                                    credentials.sortedByDescending { it.lastEdit }
                            }
                        }
                    }
                }
            }

            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                filteredVaults = if (searchQuery.isEmpty()) {
                    credentials
                } else {
                    val resultVault = mutableListOf<Credential>()
                    credentials.forEach { credential ->
                        if (credential.website.lowercase()
                                .contains(searchQuery.lowercase())) {
                            resultVault.add(credential)
                        }
                    }
                    resultVault
                }
                items(filteredVaults) { credential ->
                    CredentialRow(
                        credential = credential,
                        onClick = {
                            navController.navigate(Screens.Add.arg(credential.id))
                        },
                        onFavClicked = {
                            credential.favourite = !credential.favourite
                            viewModel.updateCredentials(credential)
                        })
                }
            }
        }
    }
}


@Composable
fun DataFilters(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: (String) -> Unit
) {
    Box(modifier = modifier
        .padding(start = 12.dp, bottom = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(if (selected) CoolBlue else if (isSystemInDarkTheme()) LiteGray else LiteText)
        .clickable { onClick(label) }
        .padding(vertical = 10.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label,
            style = MaterialTheme.typography.subtitle1
                .copy(fontWeight = FontWeight.Bold),
            color = if (selected || isSystemInDarkTheme()) Color.White else Color.DarkGray)
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun CredentialRow(
    credential: Credential,
    onClick: (Credential) -> Unit,
    onFavClicked: () -> Unit
) {
    var passwordVisibility: Boolean by mutableStateOf(false)
    var passwd by mutableStateOf("********")
    var favTint by mutableStateOf( if (credential.favourite) TomatoRed else WhiteGray)

    ConstraintLayout(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSystemInDarkTheme()) LiteGray else LiteText)
            .clickable { onClick(credential) }
            .padding(12.dp)
    ) {
        val (logo, container, fav) = createRefs()
        Image(
            modifier = Modifier
                .size(48.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            imageVector = Icons.Filled.Lock,
            contentDescription = "Logo",
            colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) LiteText else DarkGray)
        )
        Column(modifier = Modifier
            .constrainAs(container) {
                top.linkTo(parent.top)
                start.linkTo(logo.end)
            }
            .padding(horizontal = 16.dp)) {
            Text(text = credential.website, style = MaterialTheme.typography.subtitle2)
            Text(text = credential.username, style = MaterialTheme.typography.subtitle1)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = passwd, style = MaterialTheme.typography.button)
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    if (passwordVisibility) {
                        passwd = credential.password
                        Icon(imageVector = Icons.Filled.Visibility, contentDescription = "Visisble")
                    }
                    else {
                        passwd = "********"
                        Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "Visible off")
                    }
                }
            }
        }
        IconButton(
            modifier = Modifier.constrainAs(fav) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
            onClick = {
                favTint = if (credential.favourite) WhiteGray else TomatoRed
                onFavClicked()
        }) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favourite", tint = favTint)
        }
    }
}