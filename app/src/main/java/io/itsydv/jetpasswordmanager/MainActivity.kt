package io.itsydv.jetpasswordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.itsydv.jetpasswordmanager.navigation.MyNavGraph
import io.itsydv.jetpasswordmanager.ui.theme.JetPasswordManagerTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPasswordManagerTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) { MyNavGraph(navController = navController) }
            }
        }
    }
}
