package com.nanamare.base.ui.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.google.android.material.composethemeadapter.MdcTheme

@Suppress("NOTHING_TO_INLINE")
inline fun ComponentActivity.setThemeContent(
    darkTheme: Boolean = false,
    noinline content: @Composable () -> Unit
) = setContent {
    if (darkTheme) {

    } else {
        MdcTheme { content() }
    }
}