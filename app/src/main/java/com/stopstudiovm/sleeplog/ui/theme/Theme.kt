package com.stopstudiovm.sleeplog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color.White,
    background = MaterialBlue,
    onBackground = MaterialWhite,
    surface = MaterialBlue1,
    //onSurface = DarkGray
)

@Composable
fun SleepLogTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
        DarkColorPalette

    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography = Typography,
        shapes = SpacesShapes().shapes,
        content = content
    )
}