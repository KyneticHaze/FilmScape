package com.furkanhrmnc.filmscape.ui.screen.detail.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.furkanhrmnc.filmscape.common.ApiTools
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.ui.util.sharedComponents.ImagePlaceholder

@Composable
fun PosterSection(
    media: Media,
    context: Context
) {
    val posterUrl = "${ApiTools.IMAGE_URL}${media.posterPath}"

    val posterPainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(posterUrl)
            .crossfade(1000)
            .size(Size.ORIGINAL)
            .build()
    )

    Column {
        Spacer(modifier = Modifier.height(150.dp))
        Card(
            modifier = Modifier
                .size(
                    width = 180.dp,
                    height = 250.dp
                )
                .padding(start = 16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ImagePlaceholder(
                    imagePainter = posterPainter,
                    description = media.originalTitle
                )
            }
        }
    }
}