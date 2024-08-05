package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.navigation.components.Routes
import com.furkanhrmnc.filmscape.ui.components.MovieCard
import com.furkanhrmnc.filmscape.ui.components.MoviesTopBar
import com.furkanhrmnc.filmscape.ui.components.SomethingWentWrong
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimilarScreen(
    modifier: Modifier = Modifier,
    movieId: Int,
    navController: NavController
) {
    val viewModel: SimilarMoviesViewModel = koinInject { parametersOf(movieId) }
    val snackbarHostState = remember { SnackbarHostState() }
    val similarMovies = viewModel.movies.collectAsLazyPagingItems()

    SomethingWentWrong(error = viewModel.error, snackbarHostState = snackbarHostState)

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = { MoviesTopBar(appTitle = "") },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { scaffoldPadding ->
        SimilarContent(
            modifier = Modifier.padding(scaffoldPadding),
            movies = similarMovies,
            navController = navController,
            onError = viewModel::onError
        )
    }
}

@Composable
fun SimilarContent(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    navController: NavController,
    onError: (Throwable) -> Unit,
) {

    LaunchedEffect(key1 = movies.loadState) {
        when {
            movies.loadState.refresh is LoadState.Error ->
                onError(
                    (movies.loadState.refresh as LoadState.Error).error
                )

            movies.loadState.append is LoadState.Error ->
                onError(
                    (movies.loadState.append as LoadState.Error).error
                )
        }
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3)
    ) {
        items(count = movies.itemCount) { index ->
            movies[index]?.let { movie ->
                val context = LocalContext.current
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context).crossfade(400).data(movie.posterPath)
                        .error(
                            R.drawable.error
                        ).size(Size.ORIGINAL).build()
                )
                MovieCard(
                    painter = painter,
                    title = movie.title,
                    onClick = { navController.navigate("${Routes.DETAILS.route}?id=${movie.id}") })
            }
        }
    }
}