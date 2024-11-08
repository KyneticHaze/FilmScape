package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.navigation.Destinations
import com.furkanhrmnc.filmscape.util.MediaCard
import com.furkanhrmnc.filmscape.util.Snack
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimilarScreen(
    modifier: Modifier = Modifier,
    viewModel: SimilarMediasViewModel,
    navController: NavController,
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val similarMedias = viewModel.similarMedias.collectAsLazyPagingItems()
    val error by viewModel.error.collectAsState()
    val lazyGridState = rememberLazyGridState()
    val scrollToTop by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 3 } }


    Snack(
        message = error,
        snackBarHostState = snackbarHostState,
        onDismissed = viewModel::onErrorConsumed
    )


    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Recommendation Movies",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            })
        },
        floatingActionButton = {
            AnimatedVisibility(visible = scrollToTop) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            lazyGridState.animateScrollToItem(0)
                        }
                    }, containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowUpward,
                        contentDescription = "Up to Film"
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { scaffoldPadding ->
        LaunchedEffect(key1 = similarMedias) {
            when {
                similarMedias.loadState.refresh is LoadState.Error -> {
                    viewModel.onError((similarMedias.loadState.refresh as LoadState.Error).error)
                }

                similarMedias.loadState.append is LoadState.Error -> {
                    viewModel.onError((similarMedias.loadState.append as LoadState.Error).error)
                }
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.padding(scaffoldPadding),
            state = lazyGridState,
            columns = GridCells.Fixed(3)
        ) {
            items(count = similarMedias.itemCount) { index ->
                similarMedias[index]?.let { media ->
                    MediaCard(
                        media = media,
                        onCLick = { navController.navigate("${Destinations.DETAILS.route}?id=${media.id}&type=${media.type}") }
                    )
                }
            }
        }
    }
}