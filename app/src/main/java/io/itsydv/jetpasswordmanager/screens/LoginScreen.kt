package io.itsydv.jetpasswordmanager.screens

import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.itsydv.jetpasswordmanager.components.MyButton
import io.itsydv.jetpasswordmanager.components.MyInputText
import io.itsydv.jetpasswordmanager.navigation.Screens
import io.itsydv.jetpasswordmanager.util.validateLogin

@OptIn(ExperimentalTextApi::class)
@Composable
fun LoginScreen(navController: NavHostController) {

    val gradientColors = listOf(Color(0xFF03A9F4), Color(0xFF673AB7), Color(0xFFFF5722))
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Surface(color = Color.White, modifier = Modifier.verticalScroll(scrollState)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = io.itsydv.jetpasswordmanager.R.drawable.auth),
                contentDescription = "Login Header")

            Text(text = "Never forget a password Anymore",
                style = MaterialTheme.typography.h4.copy(
                    brush = Brush.linearGradient(gradientColors),
                    textAlign = TextAlign.Center,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                fontWeight = FontWeight.Bold, color = Color(0xFF2B2B2B),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 48.dp, bottom = 40.dp))

            Text(text = "Login", style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold, color = Color(0xFF2B2B2B),
                modifier = Modifier.padding(start = 24.dp, bottom = 16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                MyInputText(
                    text = username,
                    label = "Email",
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") },
                    onTextChange = { username = it },
                    textColor = Color(0xFF2B2B2B),
                    backgroundColor = Color(0xFFEBEBEB),
                    modifier = Modifier.padding(bottom = 24.dp)
                    , onImeAction = {
                        ImeAction.Next
                    })

                MyInputText(
                    text = password,
                    label = "Password",
                    password = true,
                    leadingIcon = { Icon(imageVector = Icons.Default.Key, contentDescription = "Password") },
                    onTextChange = { password = it },
                    textColor = Color(0xFF2B2B2B),
                    backgroundColor = Color(0xFFEBEBEB),
                    modifier = Modifier.padding(bottom = 40.dp))

                MyButton(text = "Login",
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(bottom = 40.dp),
                    enabled = (username.isNotEmpty() && password.isNotEmpty())
                ) {
                    val (valid, reason) = validateLogin(username, password)
                    if (valid) {
                        context.getSharedPreferences("app_preferences", MODE_PRIVATE)
                            .edit().putInt("loggedIn", 1).apply()
                        navController.navigate(Screens.Home.route) {
                            popUpTo(Screens.Home.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}