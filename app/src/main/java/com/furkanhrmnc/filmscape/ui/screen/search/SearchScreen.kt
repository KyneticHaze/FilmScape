package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.util.MediaGridOrShimmer
import com.furkanhrmnc.filmscape.util.SearchBox
import com.furkanhrmnc.filmscape.util.Snack
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchMediasViewModel = koinInject(),
    navController: NavController,
) {

    val uiState by viewModel.uiState.collectAsState()

    val searchMedias = viewModel.searchMedias.collectAsLazyPagingItems()
    val search by viewModel.search.collectAsState()

    val lazyGridState = rememberLazyGridState()
    val scrollToTop by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 3 } }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }



    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            AnimatedVisibility(
                visible = scrollToTop,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            lazyGridState.animateScrollToItem(index = 0)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = "Up")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {

            SearchBox(
                modifier = Modifier.align(Alignment.TopCenter),
                query = search,
                onQueryChange = viewModel::onSearch,
                onBack = navController::navigateUp,
                onClear = viewModel::onClear
            )

            if (uiState.isLoading) {
                MediaGridOrShimmer(
                    mediaItems = searchMedias,
                    isLoading = true,
                    lazyGridState = lazyGridState,
                    navController = navController
                )
            } else if (uiState.error != null) {
                Snack(
                    message = uiState.error,
                    snackBarHostState = snackbarHostState,
                    onDismissed = viewModel::onErrorConsumed
                )
            } else {
                MediaGridOrShimmer(
                    mediaItems = searchMedias,
                    isLoading = false,
                    lazyGridState = lazyGridState,
                    navController = navController
                )
            }
        }
    }
}