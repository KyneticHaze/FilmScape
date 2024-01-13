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
import com.example.movieland.data.remote.dto.image.Poster
import com.example.movieland.ui.screens.app_start_screen.intro.ErrorImage
import com.example.movieland.ui.screens.app_start_screen.intro.LoadingImage
import com.example.movieland.ui.screens.home_screen.util.imagePath

@Composable
fun PosterCard(
    modifier: Modifier = Modifier, poster: Poster
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier
            .size(height = 220.dp, width = 300.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        SubcomposeAsyncImage(
            model = imagePath(poster.filePath.orEmpty()),
            contentDescription = "Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.medium)
        ) {
            when (this.painter.state) {
                is AsyncImagePainter.State.Loading -> LoadingImage()
                is AsyncImagePainter.State.Error -> ErrorImage()
                else -> SubcomposeAsyncImageContent()
            }
        }
    }
}