package com.nanamare.base.ui.compose

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.nanamare.base.R

@Composable
fun EmptySnackBar(scaffoldState: ScaffoldState, block: (() -> Unit)? = null) {
    val label = stringResource(R.string.retry)

    LaunchedEffect(scaffoldState.snackbarHostState) {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = "Empty List",
            actionLabel = when (block) {
                null -> null
                else -> label
            },
            duration = SnackbarDuration.Short
        )
        when (result) {
            SnackbarResult.ActionPerformed -> block?.invoke()
            SnackbarResult.Dismissed -> Unit
        }
    }
}