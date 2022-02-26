package com.nanamare.base.ui.compose

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

// https://material.io/design/color/the-color-system.html#color-theme-creation

private val colorLightPrimary = Color(0xFFFFB400)
private val colorLightTextSecondary = Color(0xFF6C727A)
private val colorLightBackground = Color(0xFFFFFFFF)

private val colorDarkPrimary = Color(0xFFBA55D3)
private val colorDarkTextSecondary = Color(0xFF6C727A)
private val colorDarkBackground = Color(0xFF090A0A)


val LightPalette = lightColors(
    primary = colorLightPrimary,
    secondary = colorLightTextSecondary,
    background = colorLightBackground
)

val DarkPalette = darkColors(
    primary = colorDarkPrimary,
    secondary = colorDarkTextSecondary,
    background = colorDarkBackground
)


