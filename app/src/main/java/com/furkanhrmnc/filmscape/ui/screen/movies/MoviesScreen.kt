package com.furkanhrmnc.filmscape.ui.screen.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.navigation.components.Routes
import com.furkanhrmnc.filmscape.ui.components.MovieCard
import com.furkanhrmnc.filmscape.ui.components.MoviesTopBar
import com.furkanhrmnc.filmscape.ui.components.SomethingWentWrong
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.categoryResId
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    category: Category,
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
) {
    val viewModel: MoviesViewModel = koinInject<MoviesViewModel> { parametersOf(category) }
    val snackbarHostState = remember { SnackbarHostState() }
    val movies = viewModel.movies.collectAsLazyPagingItems()

    SomethingWentWrong(
        error = viewModel.error,
        snackbarHostState = snackbarHostState
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MoviesTopBar(appTitle = stringResource(id = category.categoryResId())) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { scaffoldPadding ->
        MoviesContent(
            modifier = Modifier.padding(scaffoldPadding),
            movies = movies,
            onError = viewModel::onError,
            onMovieClick = { movie -> navController.navigate("${Routes.DETAILS.route}?id=${movie.id}") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesContent(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    onError: (Throwable) -> Unit,
    onMovieClick: (Movie) -> Unit,
) {

    val refreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        LaunchedEffect(key1 = movies.loadState) {
            when {
                movies.loadState.refresh is LoadState.Error ->
                    onError((movies.loadState.refresh as LoadState.Error).error)

                movies.loadState.append is LoadState.Error ->
                    onError((movies.loadState.append as LoadState.Error).error)
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3)
        ) {
            if (!refreshState.isRefreshing) {
                items(count = movies.itemCount) { itemIndex ->
                    movies[itemIndex]?.let { movie ->
                        MovieCard(
                            modifier = Modifier.size(170.dp),
                            moviesPathString = movie.posterPath,
                            title = movie.title,
                            onClick = { onMovieClick(movie) }
                        )
                    }
                }
            }
        }
    }
}