package com.nanamare.base.ui.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

@Suppress("NOTHING_TO_INLINE")
inline fun Fragment.setThemeContent(noinline content: @Composable () -> Unit) =
    ComposeView(requireContext()).apply {
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            val colors = when {
                isDarkTheme -> LightPalette
                else -> DarkPalette
            }
            MaterialTheme(colors = colors, content = content)
        }
    }
