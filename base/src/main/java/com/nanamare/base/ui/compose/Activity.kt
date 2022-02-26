package com.nanamare.base.ui.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

fun ComponentActivity.setThemeContent(content: @Composable () -> Unit) =
    setContent {
        val isDarkTheme = isSystemInDarkTheme()
        val colors = when {
            isDarkTheme -> DarkPalette
            else -> LightPalette
        }
        MaterialTheme(colors = colors, content = content)
    }