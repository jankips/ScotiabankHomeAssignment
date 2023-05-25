package com.example.scotiabankhomeassignment.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = ColorAccent,
    secondary = Teal200,
    background = Color.White
)

@Composable
fun ScotiabankHomeAssignmentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController: SystemUiController = rememberSystemUiController()
    val context = LocalContext.current

    systemUiController.isStatusBarVisible = true // Status bar
    systemUiController.isNavigationBarVisible = true // Navigation bar
    systemUiController.isSystemBarsVisible = true // Status & Navigation bars
    LaunchedEffect(key1 = context) {
        systemUiController.setStatusBarColor(
            color = PrimaryDark, // Change this to your desired status bar color
            darkIcons = darkTheme // Set to true if you want dark icons on light status bar
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}