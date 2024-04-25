package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.usecase.ViewState
import com.furkanhrmnc.filmscape.util.ApiConfig

@Composable
fun Movies(
    modifier: Modifier = Modifier,
    moviesState: ViewState<List<Movie>>,
    onMovieClick: (movie: Movie) -> Unit
) {
    when (moviesState) {
        is ViewState.Failure -> {
            moviesState.exception?.message?.run {
                Text(text = this)
            }
        }

        ViewState.Loading -> {
            CircularProgressIndicator()
        }

        is ViewState.Success -> {
            if (moviesState.data.isNotEmpty()) {
                LazyRow(
                    modifier = modifier
                ) {
                    items(
                        items = moviesState.data,
                        key = { movie -> movie.id }
                    ) { movie ->
                        val context = LocalContext.current
                        val imageUrl = "${ApiConfig.IMAGE_URL}${movie.posterPath}"
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .crossfade(durationMillis = 1000)
                                .size(Size.ORIGINAL)
                                .data(imageUrl)
                                .build()
                        )
                        MovieCard(
                            painter = painter,
                            title = movie.title,
                            onClick = { onMovieClick(movie) },
                            background = MaterialTheme.colorScheme.background,
                            content = MaterialTheme.colorScheme.onBackground,
                            elevation = 2.dp
                        )
                    }
                }
            }
        }
    }
}