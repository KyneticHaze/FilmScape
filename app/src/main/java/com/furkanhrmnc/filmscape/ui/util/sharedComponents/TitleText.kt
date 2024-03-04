package com.furkanhrmnc.filmscape.ui.util.sharedComponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.furkanhrmnc.filmscape.ui.theme.filmScapeFont

@Composable
fun TitleText(
    text: String
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = filmScapeFont,
        fontSize = 22.sp
    )
}