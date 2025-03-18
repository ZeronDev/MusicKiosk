package com.example.mkiosk.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat


val mainColorScheme = lightColorScheme(
    primary = Salesio,
    secondary = BlackGray,
    tertiary = Gray,
    onPrimary = White
)

@Composable
fun MkioskTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = mainColorScheme.secondary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).hide(WindowInsetsCompat.Type.systemBars())
        }
    }

    MaterialTheme(
        colorScheme = mainColorScheme,
        typography = Typography,
        content = content
    )
}