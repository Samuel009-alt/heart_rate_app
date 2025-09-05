package com.example.heart_rate_app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Dark Theme
val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    onPrimary = PureWhite,
    primaryContainer = DarkBlue, // Use DarkBlue for container
    onPrimaryContainer = PureWhite,

    secondary = LightBlue, // Use your LightBlue as secondary
    onSecondary = SoftBlack,
    secondaryContainer = LightBlue.copy(alpha = 0.2f),
    onSecondaryContainer = LightBlue,

    background = SoftBlack,
    onBackground = OffWhite,

    surface = SoftBlack,
    onSurface = OffWhite,

    surfaceVariant = SoftBlack.copy(alpha = 0.8f),
    onSurfaceVariant = LightGray, // Use your LightGray

    error = Color(0xFFCF6679),
    onError = PureWhite,

    outline = LightGray, // Use LightGray for outlines
    outlineVariant = LightGray.copy(alpha = 0.5f)
)

// Light Theme (optional, if you want to support both)
val LightColorScheme = lightColorScheme(
    primary = DarkBlue,
    onPrimary = PureWhite,
    primaryContainer = DarkBlue.copy(alpha = 0.1f),
    onPrimaryContainer = DarkBlue,

    secondary = LightBlue,
    onSecondary = PureWhite,
    secondaryContainer = LightBlue.copy(alpha = 0.1f),
    onSecondaryContainer = LightBlue,

    background = PureWhite,
    onBackground = Color.Black,

    surface = PureWhite,
    onSurface = Color.Black,

    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = LightGray,

    error = Color(0xFFB00020),
    onError = PureWhite,

    outline = LightGray,
    outlineVariant = LightGray.copy(alpha = 0.3f)
)

@Composable
fun Heart_Rate_AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}