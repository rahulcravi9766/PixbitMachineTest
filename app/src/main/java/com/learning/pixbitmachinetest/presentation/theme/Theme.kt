package com.learning.pixbitmachinetest.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = primary_dark,
    secondary = secondary_dark,
    background = background_dark,
    surface = surface_dark,
    onPrimary = onPrimary_dark,
    onSecondary = onSecondary_dark,
    onBackground = onBackground_dark,
    onSurface = onSurface_dark,
)

private val LightColorScheme = lightColorScheme(
    primary = primary_light,
    secondary = secondary_light,
    background = background_light,
    surface = surface_light,
    onPrimary = onPrimary_light,
    onSecondary = onSecondary_light,
    onBackground = onBackground_light,
    onSurface = onSurface_light,
)

@Composable
fun PixbitMachineTestTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}