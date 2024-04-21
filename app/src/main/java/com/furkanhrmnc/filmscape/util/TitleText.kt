package com.furkanhrmnc.filmscape.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.furkanhrmnc.filmscape.presentation.theme.filmScapeFont

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        fontFamily = filmScapeFont,
        fontSize = 22.sp
    )
}