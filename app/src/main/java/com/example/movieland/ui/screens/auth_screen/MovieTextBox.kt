package com.example.movieland.ui.screens.auth_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MovieTextBox(
    text: String,
    labelText: String,
    leadIcon: ImageVector,
    trailIcon: Int,
    desc: String,
    keyboardOp: KeyboardOptions = KeyboardOptions.Default,
    onTextChange: (String) -> Unit,
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .border(1.dp, MaterialTheme.colorScheme.onSurface, shape = MaterialTheme.shapes.medium),
        label = { Text(text = labelText) },
        leadingIcon = {
            Icon(imageVector = leadIcon, contentDescription = desc)
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = trailIcon),
                contentDescription = desc,
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
        ),
        keyboardOptions = keyboardOp,
        maxLines = 1,
        singleLine = true
    )
}