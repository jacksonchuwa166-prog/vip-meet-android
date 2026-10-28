package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val VipDarkColorScheme = darkColorScheme(
    primary = VipPurpleLight,
    onPrimary = VipWhite,
    primaryContainer = VipPurpleContainer,
    onPrimaryContainer = VipPurpleGlow,
    secondary = VipGoldPrimary,
    onSecondary = VipObsidianBg,
    secondaryContainer = VipDarkCardElevated,
    onSecondaryContainer = VipGoldLight,
    tertiary = VipGoldGlow,
    onTertiary = VipObsidianBg,
    background = VipObsidianBg,
    onBackground = VipTextPrimary,
    surface = VipDarkSurface,
    onSurface = VipTextPrimary,
    surfaceVariant = VipDarkCard,
    onSurfaceVariant = VipTextSecondary,
    outline = VipBorderDark,
    error = VipDanger,
    onError = VipWhite
)

@Composable
fun VipMeetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // VIP MEET is a dedicated premium dark-aesthetic experience (black, purple, and gold)
    val colorScheme = VipDarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = VipObsidianBg.toArgb()
                window.navigationBarColor = VipObsidianBg.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
