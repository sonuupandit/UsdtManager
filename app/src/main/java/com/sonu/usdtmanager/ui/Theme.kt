package com.sonu.usdtmanager.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Green = Color(0xFF16A34A)
private val Red = Color(0xFFDC2626)
private val Bg = Color(0xFFF8FAF7)
private val DarkBg = Color(0xFF0F1512)

private val LightColors = lightColorScheme(
    primary = Green,
    error = Red,
    background = Bg
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF4ADE80),
    error = Color(0xFFF87171),
    background = DarkBg
)

@Composable
fun UsdtManagerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        content = content
    )
}

fun formatInr(v: Double): String {
    val n = java.text.DecimalFormat("#,##0")
    return "₹" + n.format(v)
}

fun formatQty(v: Double): String =
    java.text.DecimalFormat("#,##0.00").format(v) + " USDT"
