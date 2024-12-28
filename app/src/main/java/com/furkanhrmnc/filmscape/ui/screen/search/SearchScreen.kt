package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.util.Constants.UNKNOWN_ERROR
import com.furkanhrmnc.filmscape.util.MediaGridOrShimmer
import com.furkanhrmnc.filmscape.util.ScrollToUp
import com.furkanhrmnc.filmscape.util.SearchBox
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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(message = it.message ?: UNKNOWN_ERROR)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            ScrollToUp(scrollToTop = scrollToTop, lazyGridState = lazyGridState)
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
                SnackbarHost(hostState = snackbarHostState)
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