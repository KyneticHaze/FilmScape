package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.navigation.Destinations
import com.furkanhrmnc.filmscape.util.MediaCard
import com.furkanhrmnc.filmscape.util.ScrollToUp
import com.furkanhrmnc.filmscape.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimilarScreen(
    modifier: Modifier = Modifier,
    viewModel: SimilarMediasViewModel,
    navController: NavController,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val uiEvent = viewModel.uiEvent.collectAsState(initial = null).value

    LaunchedEffect(uiEvent) {
        when (uiEvent) {
            is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(
                message = uiEvent.message,
                actionLabel = uiEvent.action
            )

            else -> Unit
        }
    }

    val similarMedias = viewModel.similarMedias.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()
    val scrollToTop by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 3 } }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.recommendations),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            })
        },
        floatingActionButton = {
            ScrollToUp(scrollToTop = scrollToTop, lazyGridState = lazyGridState)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { scaffoldPadding ->
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            state = lazyGridState,
            columns = GridCells.Fixed(3),
            contentPadding = scaffoldPadding
        ) {
            items(
                count = similarMedias.itemCount,
                key = { index -> similarMedias[index]?.id ?: index }
            ) { index ->
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