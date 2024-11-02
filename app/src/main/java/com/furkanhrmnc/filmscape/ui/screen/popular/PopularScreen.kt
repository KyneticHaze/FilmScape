package com.furkanhrmnc.filmscape.ui.screen.popular

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.navigation.components.Destinations
import com.furkanhrmnc.filmscape.util.MediaCard
import com.furkanhrmnc.filmscape.util.Snack
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val viewModel = koinViewModel<PopularViewModel>()
    val popularMedias = viewModel.popularMedias.collectAsLazyPagingItems()
    val error = viewModel.error.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridState()
    val scrollToTop by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 3 } }

    Snack(
        message = error.value,
        snackBarHostState = snackBarHostState,
        onDismissed = viewModel::onErrorConsumed,
        label = "Ok"
    )

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(text = "Trending All") }) },
        floatingActionButton = {
            AnimatedVisibility(visible = scrollToTop) {
                FloatingActionButton(onClick = {
                    scope.launch {
                        lazyGridState.animateScrollToItem(0)
                    }
                }) {
                    Icon(imageVector = Icons.Outlined.ArrowUpward, contentDescription = "Go up")
                }
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
            state = lazyGridState,
            contentPadding = padding
        ) {

            when {
                (popularMedias.loadState.refresh is LoadState.Error) -> {
                    viewModel.onError((popularMedias.loadState.refresh as LoadState.Error).error)
                }

                (popularMedias.loadState.append is LoadState.Error) -> {
                    viewModel.onError((popularMedias.loadState.refresh as LoadState.Error).error)
                }
            }


            items(popularMedias.itemCount) { index ->
                popularMedias[index]?.let { popularMedia ->
                    MediaCard(
                        media = popularMedia,
                        clickable = { id ->
                            navController.navigate("${Destinations.DETAILS.route}?id=$id")
                        }
                    )
                }
            }
        }
    }
}