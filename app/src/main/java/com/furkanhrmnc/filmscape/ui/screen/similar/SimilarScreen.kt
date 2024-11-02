package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.navigation.components.Destinations
import com.furkanhrmnc.filmscape.util.MediaCard
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimilarScreen(
    modifier: Modifier = Modifier,
    movieId: Int,
    navController: NavController,
) {

    val viewModel: SimilarMoviesViewModel = koinInject { parametersOf(movieId) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val similarMovies = viewModel.movies.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()
    val scrollToTop by remember {
        derivedStateOf { lazyGridState.firstVisibleItemIndex > 3 }
    }


    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Recommendation Movies") }) },
        floatingActionButton = {
            AnimatedVisibility(visible = scrollToTop) {
                FloatingActionButton(onClick = {
                    scope.launch {
                        lazyGridState.animateScrollToItem(0)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowUpward,
                        contentDescription = "Up to Film"
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                viewModel.error?.let { error ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = error.message ?: "Unknown Error",
                            actionLabel = "Similar Data Load Error"
                        )
                    }
                }
            }
        }
    ) { scaffoldPadding ->
        SimilarContent(
            modifier = Modifier.padding(scaffoldPadding),
            similarMedias = similarMovies,
            lazyGridState = lazyGridState,
            onError = viewModel::onError,
            onClick = { id -> navController.navigate("${Destinations.DETAILS.route}?id=${id}") }
        )
    }
}

@Composable
fun SimilarContent(
    modifier: Modifier = Modifier,
    similarMedias: LazyPagingItems<Media>,
    lazyGridState: LazyGridState,
    onError: (Throwable) -> Unit,
    onClick: (Int) -> Unit,
) {
    LaunchedEffect(key1 = similarMedias) {
        when {
            similarMedias.loadState.refresh is LoadState.Error -> {
                onError((similarMedias.loadState.refresh as LoadState.Error).error)
            }

            similarMedias.loadState.append is LoadState.Error -> {
                onError((similarMedias.loadState.append as LoadState.Error).error)
            }
        }
    }

    LazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        columns = GridCells.Fixed(3)
    ) {
        items(count = similarMedias.itemCount) { index ->
            similarMedias[index]?.let { media ->
                MediaCard(
                    media = media,
                    clickable = { id -> onClick(id) }
                )
            }
        }
    }
}