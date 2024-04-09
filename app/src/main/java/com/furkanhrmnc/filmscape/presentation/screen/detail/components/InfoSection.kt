package com.furkanhrmnc.filmscape.presentation.screen.detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.furkanhrmnc.filmscape.util.Constants
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.presentation.screen.detail.DetailUIState
import com.furkanhrmnc.filmscape.presentation.theme.filmScapeFont

@Composable
fun InfoSection(
    media: Media,
    detailUiState: DetailUIState
) {

    val releaseDate =
        if (media.releaseDate != Constants.UNAVAILABLE) media.releaseDate.take(4) else ""
    val adultLimit = if (media.adult) "+18" else "-12"

    Column {
        Spacer(modifier = Modifier.height(180.dp))
        Text(
            text = media.originalTitle,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            fontFamily = filmScapeFont,
            fontSize = 19.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBar(
                starsModifier = Modifier.size(18.dp),
                rating = media.rating.div(2)
            )
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = media.rating.toString().take(3),
                fontFamily = filmScapeFont,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.background
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = releaseDate,
                color = MaterialTheme.colorScheme.background,
                fontSize = 15.sp,
                fontFamily = filmScapeFont
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(6.dp)),
                text = adultLimit,
                color = MaterialTheme.colorScheme.background,
                fontFamily = filmScapeFont,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.padding(end = 6.dp),
            text = detailUiState.time,
            fontFamily = filmScapeFont,
            color = MaterialTheme.colorScheme.background,
            lineHeight = 14.sp
        )
    }
}