package com.furkanhrmnc.filmscape.util

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.furkanhrmnc.filmscape.util.Constants.UNKNOWN_ERROR

@Composable
fun Snack(
    message: Throwable? = null,
    snackBarHostState: SnackbarHostState,
    duration: SnackbarDuration = SnackbarDuration.Short,
    label: String? = null,
    onDismissed: (() -> Unit)? = null,
    onActionPerformed: (() -> Unit)? = null,
) {

    if (message == null) return

    LaunchedEffect(key1 = snackBarHostState) {
        when (
            snackBarHostState.showSnackbar(
                message = message.message ?: UNKNOWN_ERROR,
                duration = duration,
                actionLabel = label
            )) {
            SnackbarResult.Dismissed -> onDismissed?.invoke()
            SnackbarResult.ActionPerformed -> onActionPerformed?.invoke()
        }
    }
}