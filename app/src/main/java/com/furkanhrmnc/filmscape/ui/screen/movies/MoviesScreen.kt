package com.furkanhrmnc.filmscape.ui.screen.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.navigation.components.Routes
import com.furkanhrmnc.filmscape.ui.components.AppTopBar
import com.furkanhrmnc.filmscape.ui.components.MovieCard
import com.furkanhrmnc.filmscape.ui.components.SomethingWentWrong
import com.furkanhrmnc.filmscape.util.ApiConfig
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.categoryResId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    category: Category,
    viewModel: MoviesViewModel = hiltViewModel(),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    navController: NavController,
) {


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
        topBar = {
            AppTopBar(
                appTitle = stringResource(id = category.categoryResId())
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { scaffoldPadding ->
        MoviesContent(
            contentPadding = scaffoldPadding,
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
    contentPadding: PaddingValues,
    movies: LazyPagingItems<Movie>,
    onError: (Throwable) -> Unit,
    onMovieClick: (Movie) -> Unit,
) {

    val refreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(contentPadding)
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

                        val context = LocalContext.current
                        val imageUrl = "${ApiConfig.IMAGE_URL}${movie.posterPath}"
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .crossfade(1000)
                                .data(imageUrl)
                                .build()
                        )
                        MovieCard(
                            modifier = Modifier
                                .height(180.dp),
                            painter = painter,
                            scale = ContentScale.Crop,
                            onClick = { onMovieClick(movie) }
                        )
                    }
                }
            }
        }
    }
}