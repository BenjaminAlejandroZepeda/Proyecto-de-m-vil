package com.example.legendarymotorsport_app.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = Color(0xFFFFD60A),
    secondary = Color(0xFF14213D),
    background = Color.White,
    surface = Color(0xFFF8F9FA),
    onPrimary = Color(0xFF14213D),
    onSecondary = Color.White,
    onBackground = Color(0xFF212529),
    onSurface = Color(0xFF6C757D),
    error = Color(0xFFDC3545),
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFFFD60A),
    secondary = Color(0xFF14213D),
    background = Color(0xFF0D0D0D),
    surface = Color(0xFF1C1C1E),
    onPrimary = Color(0xFF0D0D0D),
    onSecondary = Color.White,
    onBackground = Color(0xFFF8F9FA),
    onSurface = Color(0xFFCED4DA),
    error = Color(0xFFDC3545),
    onError = Color.White
)

private val AppTypography = Typography(
    displayLarge = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Bold),
    displayMedium = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.SemiBold),
    displaySmall = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontSize = 16.sp),
    bodyMedium = TextStyle(fontSize = 14.sp),
    labelLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
)

@Composable
fun LegendaryMotorSportTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(colorScheme = colors, typography = AppTypography, content = content)
}
