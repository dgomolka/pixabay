package com.psycodeinteractive.pixabay.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

private val themeColorsLight = lightColors(
    primary = Color(0xFF45875B),
    primaryVariant = Color(0xFF333333),
    onPrimary = Color.White,
    secondary = Color.White,
    onSecondary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    background = Color.DarkGray.copy(alpha = 0.5f),
    onBackground = Color.White
)

private val themeColorsDark = darkColors(
    primary = Color(0xFF45875B),
    primaryVariant = Color(0xFF333333),
    onPrimary = Color.White,
    secondary = Color.DarkGray,
    onSecondary = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    background = Color.DarkGray.copy(alpha = 0.5f),
    onBackground = Color.White
)

@Composable
fun PixabayTheme(children: @Composable () -> Unit) = MaterialTheme(
    colors = if (isSystemInDarkTheme()) themeColorsDark else themeColorsLight,
    typography = themeTypography
) {
    MaterialTheme {
        Surface(
            modifier = Modifier.background(MaterialTheme.colors.surface)
        ) {
            children()
        }
    }
}
