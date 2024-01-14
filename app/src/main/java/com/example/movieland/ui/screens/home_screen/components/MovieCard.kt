package com.example.movieland.ui.screens.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.movieland.R
import com.example.movieland.data.remote.dto.commonDto.MovieDTO
import com.example.movieland.ui.screens.app_start_screen.intro.ErrorImage
import com.example.movieland.ui.screens.app_start_screen.intro.LoadingImage
import com.example.movieland.ui.screens.home_screen.util.imagePath

@Composable
fun MovieCard(
    modifier: Modifier = Modifier, movie: MovieDTO, onMovieClick: (Int) -> Unit = {}
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier
            .size(200.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                movie.id?.let { onMovieClick(it) }
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = imagePath(movie.posterPath.orEmpty()),
                contentDescription = movie.title,
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
}