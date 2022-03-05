package com.nanamare.base.ui.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

fun ComponentActivity.setThemeContent(content: @Composable () -> Unit) =
    setContent {
        val isDarkTheme = isSystemInDarkTheme()
        val colors = when {
            isDarkTheme -> DarkPalette
            else -> LightPalette
        }
        val systemUiController = rememberSystemUiController()
        if (isDarkTheme) {
            systemUiController.setSystemBarsColor(color = Color.Transparent)
        } else {
            systemUiController.setSystemBarsColor(color = Color.White)
        }
        MaterialTheme(colors = colors, content = content)
    }