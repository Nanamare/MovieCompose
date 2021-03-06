package com.nanamare.base.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

@Suppress("NOTHING_TO_INLINE")
inline fun Fragment.setThemeContent(noinline content: @Composable () -> Unit) =
    ComposeView(requireContext()).apply {
        setContent {
            MovieTheme {
                content()
            }
        }
    }
