package com.example.movieland.ui.screens.home_screen.detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movieland.data.remote.dto.commonDto.MovieDTO
import com.example.movieland.ui.screens.home_screen.components.MovieCard

@Composable
fun SimilarMovieList(
    movies: List<MovieDTO>,
    title: String,
    onNavigateMovieTopic: (Int) -> Unit = {}
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(8.dp)
    )
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie) {
                onNavigateMovieTopic(it)
            }
        }
    }
}