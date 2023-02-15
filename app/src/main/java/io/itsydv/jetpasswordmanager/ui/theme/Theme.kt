package io.itsydv.jetpasswordmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = CoolBlue,
    primaryVariant = CoolBlue,
    secondary = CoolBlue
)

private val LightColorPalette = lightColors(
    primary = CoolBlue,
    primaryVariant = CoolBlue,
    secondary = CoolBlue

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun JetPasswordManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

    val systemUiController = rememberSystemUiController()
    if (darkTheme){
        systemUiController.setSystemBarsColor(
            color = DarkGray,
            darkIcons = false)
    } else  {
        systemUiController.setSystemBarsColor(color = Color.White)
    }
}