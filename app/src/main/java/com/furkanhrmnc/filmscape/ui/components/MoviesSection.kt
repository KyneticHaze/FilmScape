package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.util.ViewState

@Composable
fun MoviesSection(
    modifier: Modifier = Modifier,
    moviesState: ViewState<List<Movie>>,
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.primary,
    tintColor: Color = MaterialTheme.colorScheme.primary,
    onMovieClick: (movie: Movie) -> Unit,
    onMore: () -> Unit,
) {
    when (moviesState) {
        is ViewState.Failure -> Text(
            text = moviesState.throwable?.message ?: "Invalid movies state"
        )

        ViewState.Loading -> {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ShimmerBox(modifier = Modifier.size(height = 30.dp, width = 70.dp))
                    ShimmerBox(modifier = Modifier.size(height = 30.dp, width = 40.dp))
                }
                LazyRow {
                    items(count = 20) {
                        ShimmerBox(
                            modifier = Modifier
                                .size(height = 240.dp, width = 170.dp)
                                .padding(6.dp)
                        )
                    }
                }
            }
        }

        is ViewState.Success -> {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 6.dp)
                        .clickable { onMore() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = titleColor
                    )
                    IconButton(onClick = onMore) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = title,
                            tint = tintColor
                        )
                    }
                }
                LazyRow {
                    items(
                        items = moviesState.data,
                        key = { movie -> movie.id }
                    ) { movie ->
                        MovieCard(
                            modifier = Modifier.size(height = 240.dp, width = 170.dp),
                            moviesPathString = movie.posterPath,
                            title = movie.title,
                            titleStyle = MaterialTheme.typography.bodyLarge,
                            onClick = { onMovieClick(movie) }
                        )
                    }
                }
            }
        }
    }
}