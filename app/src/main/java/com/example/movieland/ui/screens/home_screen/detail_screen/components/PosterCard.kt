package com.example.movieland.ui.screens.home_screen.detail_screen.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.movieland.core.Constants
import com.example.movieland.data.remote.api.MediaApi
import com.example.movieland.data.remote.dto.image.Poster

@Composable
fun PosterCard(
    modifier: Modifier = Modifier,
    poster: Poster
) {


    val posterUrl = "${MediaApi.IMAGE_URL}${poster.filePath}" ?: Constants.unavailable

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier
            .size(height = 220.dp, width = 300.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        SubcomposeAsyncImage(
            model = posterUrl,
            contentDescription = "Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.medium)
        )
    }
}