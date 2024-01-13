package com.example.movieland.ui.screens.home_screen.detail_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.movieland.data.remote.dto.commonDto.MovieDTO
import com.example.movieland.data.remote.dto.credit.Cast
import com.example.movieland.ui.screens.app_start_screen.intro.ErrorImage
import com.example.movieland.ui.screens.app_start_screen.intro.LoadingImage
import com.example.movieland.ui.screens.home_screen.util.imagePath

@Composable
fun CastCard(
    modifier: Modifier = Modifier, cast: Cast
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = imagePath(cast.profilePath.orEmpty()),
                contentDescription = cast.originalName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(CircleShape)
            ) {
                when (this.painter.state) {
                    is AsyncImagePainter.State.Loading -> LoadingImage()
                    is AsyncImagePainter.State.Error -> ErrorImage()
                    else -> SubcomposeAsyncImageContent()
                }
            }
        }
    }
}