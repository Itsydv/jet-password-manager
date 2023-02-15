package io.itsydv.jetpasswordmanager.screens

import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun AppScreen(navController: NavHostController, viewModel: MainViewModel) {
    val context = LocalContext.current
    val userLoggedIn = (context.getSharedPreferences("app_preferences", MODE_PRIVATE).getInt("loggedIn", 0) == 1)
    if (userLoggedIn) {
        ListCredentialsScreen(navController, viewModel)
    } else {
        LoginScreen(navController)
    }
}