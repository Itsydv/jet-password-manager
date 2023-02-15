package io.itsydv.jetpasswordmanager.navigation

import java.util.UUID

sealed class Screens(val route: String) {
    object Startup: Screens("app_screen")
    object Login: Screens("login_screen")
    object Home: Screens("home_route")
    object Add: Screens("add_credential" + "?id={id}") {
        fun arg(id: UUID): String {
            return  this.route.replace("{id}", id.toString())
        }
    }
}