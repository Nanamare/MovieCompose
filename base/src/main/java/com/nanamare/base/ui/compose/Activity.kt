package com.nanamare.base.ui.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

fun ComponentActivity.setThemeContent(content: @Composable () -> Unit) = setContent {
    MovieTheme(content = content)
}