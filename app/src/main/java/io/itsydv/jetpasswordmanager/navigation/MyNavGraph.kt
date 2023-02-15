package io.itsydv.jetpasswordmanager.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.itsydv.jetpasswordmanager.screens.*

@Composable
fun MyNavGraph(navController: NavHostController) {

    val mainViewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.Startup.route
    ) {
        composable(route = Screens.Startup.route) {
            AppScreen(navController = navController, viewModel = mainViewModel)
        }
        composable(route = Screens.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screens.Home.route) {
            ListCredentialsScreen(navController = navController, viewModel = mainViewModel)
        }
        composable(route = Screens.Add.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                nullable = true
            })) {
            val id = it.arguments?.getString("id")
            AddUpdateScreen(navController = navController, viewModel = mainViewModel, id = id)
        }
    }
}