package com.furkanhrmnc.filmscape.presentation.screen.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.furkanhrmnc.filmscape.presentation.theme.filmScapeFont

@Composable
fun ShimmerSection(
    modifier: Modifier = Modifier,
    title: String,
    paddingEnd: Dp
) {

    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.background,
            fontFamily = filmScapeFont,
            fontSize = 22.sp
        )

        LazyRow(
            content = {
                items(10) {
                    Box(
                        modifier = modifier
                            .padding(
                                end = if (it == 9) paddingEnd else 0.dp
                            )
                    )
                }
            }
        )
    }
}