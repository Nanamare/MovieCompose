package com.nanamare.base.ui.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController


object MovieTheme {
    val colors: ThemeColors @Composable get() = LocalThemeColors.current
}

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

/**
 * Movie Theme 와 Material Theme 를 혼용해서 사용하고 있고, 이후에는 Movie Theme 로 모두 넣어주는 것이 목표
 */
@Composable
fun MovieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = getThemeColors(darkTheme)
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = when {
            darkTheme -> Color.Transparent
            else -> Color.White
        }
    )
    ProvideMovieTheme(colors) {
        MaterialTheme(colors = getColors(darkTheme), content = content)
    }
}

// Colors
@Composable
private fun getColors(darkTheme: Boolean): Colors = when (darkTheme) {
    true -> DarkPalette
    else -> LightPalette
}

// Reference color
@Composable
private fun getThemeColors(darkTheme: Boolean) = when (darkTheme) {
    true -> darkPalette()
    else -> lightPalette()
}

private val LocalThemeColors = staticCompositionLocalOf<ThemeColors> {
    error("No Theme color provided")
}

@Composable
fun ProvideMovieTheme(
    colors: ThemeColors,
    content: @Composable () -> Unit
) {
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)
    CompositionLocalProvider(
        LocalThemeColors provides provideColors,
        content = content
    )
}

@Stable
fun darkPalette(
    primary: Color = DarkPalette.primary,
    surface: Color = DarkPalette.surface,
    error: Color = DarkPalette.error,
    onSurface: Color = DarkPalette.onSurface,
    background: Color = DarkPalette.background,
    secondary: Color = DarkPalette.secondary
): DarkColors = DarkColors(
    primary,
    surface,
    error,
    onSurface,
    background,
    secondary
)

@Stable
class DarkColors(
    primary: Color,
    surface: Color,
    error: Color,
    onSurface: Color,
    background: Color,
    secondary: Color
) : ThemeColors(primary, surface, error, onSurface, background, secondary) {

    override fun copy(): ThemeColors = DarkColors(
        primary,
        surface,
        error,
        onSurface,
        background,
        secondary
    )

    override fun update(other: ThemeColors) {
        primary = other.primary
        surface = other.surface
        error = other.error
        onSurface = other.onSurface
        background = other.background
        secondary = other.secondary
    }
}

@Stable
fun lightPalette(
    primary: Color = LightPalette.primary,
    surface: Color = LightPalette.surface,
    error: Color = LightPalette.error,
    onSurface: Color = LightPalette.onSurface,
    background: Color = LightPalette.background,
    secondary: Color = LightPalette.secondary
): LightColors = LightColors(
    primary,
    surface,
    error,
    onSurface,
    background,
    secondary
)

@Stable
class LightColors(
    primary: Color,
    surface: Color,
    error: Color,
    onSurface: Color,
    background: Color,
    secondary: Color,
) : ThemeColors(primary, surface, error, onSurface, background, secondary) {
    override fun copy(): LightColors = LightColors(
        primary,
        surface,
        error,
        onSurface,
        background,
        secondary,
    )

    override fun update(other: ThemeColors) {
        primary = other.primary
        surface = other.surface
        error = other.error
        onSurface = other.onSurface
        background = other.background
        secondary = other.secondary
    }
}

@Stable
abstract class ThemeColors(
    primary: Color,
    surface: Color,
    error: Color,
    onSurface: Color,
    background: Color,
    secondary: Color,
) {
    var primary by mutableStateOf(primary)
    var surface by mutableStateOf(surface)
    var error by mutableStateOf(error)
    var onSurface by mutableStateOf(onSurface)
    var background by mutableStateOf(background)
    var secondary by mutableStateOf(secondary)

    abstract fun copy(): ThemeColors
    abstract fun update(other: ThemeColors)

    override fun toString(): String {
        return "Colors(" +
                "primary=$primary, " +
                "secondary=$secondary, " +
                "background=$background, " +
                "surface=$surface, " +
                "error=$error, " +
                "onSurface=$onSurface, " +
                ")"
    }
}


