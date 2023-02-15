package io.itsydv.jetpasswordmanager.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.itsydv.jetpasswordmanager.components.MyButton
import io.itsydv.jetpasswordmanager.components.MyInputText
import io.itsydv.jetpasswordmanager.model.Credential
import io.itsydv.jetpasswordmanager.ui.theme.DarkGray
import io.itsydv.jetpasswordmanager.ui.theme.LiteGray
import io.itsydv.jetpasswordmanager.ui.theme.LiteText
import java.time.Instant
import java.util.*

@Composable
fun AddUpdateScreen(navController: NavHostController, viewModel: MainViewModel, id: String?) {
    var credential by remember { mutableStateOf<Credential?>(null) }
    var website by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if (id != null) {
        LaunchedEffect(id) {
            credential = viewModel.getCredential(id)
            website = credential!!.website
            username = credential!!.username
            password = credential!!.password
        }
    }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController, id) {
            viewModel.deleteCredentials(credential!!)
            Toast.makeText(context, "Vault deleted", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        } },
    ) { paddingValues ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .scrollable(state = scrollState, orientation = Orientation.Vertical)
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 40.dp)
            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (isSystemInDarkTheme()) LiteGray else LiteText)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    MyInputText(
                        text = website,
                        label = "Website",
                        leadingIcon = { Icon(imageVector = Icons.Default.Link, contentDescription = "Email") },
                        backgroundColor = if (isSystemInDarkTheme()) DarkGray else Color(0xFFE0E0E0),
                        onTextChange = { website = it },
                        modifier = Modifier.padding(bottom = 24.dp))

                    MyInputText(
                        text = username,
                        label = "Username",
                        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") },
                        backgroundColor = if (isSystemInDarkTheme()) DarkGray else Color(0xFFE0E0E0),
                        onTextChange = { username = it },
                        modifier = Modifier.padding(bottom = 24.dp))

                    MyInputText(
                        text = password,
                        label = "Password",
                        password = true,
                        leadingIcon = { Icon(imageVector = Icons.Default.Key, contentDescription = "Password") },
                        backgroundColor = if (isSystemInDarkTheme()) DarkGray else Color(0xFFE0E0E0),
                        onTextChange = { password = it },
                        modifier = Modifier.padding(bottom = 40.dp))
                }

                MyButton(text = if (id == null) "Create new Vault" else "Update vault",
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(bottom = 20.dp),
                    enabled = (website.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty())
                ) {
                    if (id == null) {
                        viewModel.addCredentials(Credential(
                            website=website.trim(),
                            username = username.trim(),
                            password = password
                        ))
                        Toast.makeText(context, "New Vault created", Toast.LENGTH_SHORT).show()
                    } else {
                        val newCredential = credential?.copy(
                            website = website.trim(),
                            username = username.trim(),
                            password = password
                        )
                        if (newCredential == credential) {
                            Toast.makeText(context, "Data not changed", Toast.LENGTH_SHORT).show()
                            return@MyButton
                        }
                        else
                            newCredential?.let {
                                it.lastEdit = Date.from(Instant.now())
                                viewModel.updateCredentials(it)
                                Toast.makeText(context, "Vault updated", Toast.LENGTH_SHORT).show()
                            }
                    }
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun TopBar(navController: NavHostController, id: String?, onDelete: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 48.dp),
                text = if (id == null) "Create new Vault" else "Edit your Vault",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    textAlign = TextAlign.Center
                )
            )
        },
        backgroundColor = if (isSystemInDarkTheme()) LiteGray else LiteText,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            id?.let {
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Vault")
                }
            }
        }
    )
}