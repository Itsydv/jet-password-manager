package io.itsydv.jetpasswordmanager.util

import io.itsydv.jetpasswordmanager.BuildConfig

fun validateLogin(username: String, password: String): Pair<Boolean, String?> {
    val email = username.trim()
    val passwd = password.trim()
    var valid = false
    var reason: String? = null
    if (email.isEmpty())
        reason = "Please fill in Username"
    else if (passwd.isEmpty())
        reason = "Please fill in Password"
    else if (email != BuildConfig.LOGIN_EMAIL || passwd != BuildConfig.LOGIN_PASSWORD) {
        reason = "Incorrect credentials"
    } else {
        valid = true
    }
    return Pair(valid, reason)
}