package com.furkanhrmnc.filmscape.presentation.screen.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.presentation.theme.filmScapeFont

@Composable
fun OverviewSection(
    media: Media
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = buildString {
                append("\"")
                media.originalTitle
                append("\"")
            },
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.background,
            fontStyle = FontStyle.Italic,
            fontFamily = filmScapeFont,
            lineHeight = 12.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.overview),
            color = MaterialTheme.colorScheme.background,
            fontSize = 16.sp,
            fontFamily = filmScapeFont,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 14.sp
        )
        Text(
            text = media.overview,
            fontFamily = filmScapeFont,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.background,
            lineHeight = 14.sp
        )
    }
}