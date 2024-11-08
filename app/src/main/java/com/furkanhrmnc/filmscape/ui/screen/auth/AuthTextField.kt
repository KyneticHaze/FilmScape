package com.furkanhrmnc.filmscape.ui.screen.auth

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    label: (@Composable () -> Unit)?,
    applyVisualTransformation: Boolean = true,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        label = label,
        visualTransformation = if (applyVisualTransformation) PasswordVisualTransformation() else VisualTransformation.None,
    )
}