package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SomethingWentWrong(
    error: Throwable?,
    snackbarHostState: SnackbarHostState,
    onDismissed: (() -> Unit)? = null,
    onActionPerformed: (() -> Unit)? = null
) {
    if (error == null) return

    LaunchedEffect(key1 = snackbarHostState) {
        when(snackbarHostState.showSnackbar(message = error.message ?: "Error occured!")) {
            SnackbarResult.Dismissed -> onDismissed?.invoke()
            SnackbarResult.ActionPerformed -> onActionPerformed?.invoke()
        }
    }
}