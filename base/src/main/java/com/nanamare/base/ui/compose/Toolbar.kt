package com.nanamare.base.ui.compose

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTopBar(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    TopAppBar(
        title = content,
        navigationIcon = navigationIcon,
        modifier = modifier.fillMaxWidth(),
        elevation = 0.dp,
        actions = actions
    )
}