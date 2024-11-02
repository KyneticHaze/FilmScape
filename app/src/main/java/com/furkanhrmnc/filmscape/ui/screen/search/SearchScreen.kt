package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.util.Constants.SNACK_LABEL_OK
import com.furkanhrmnc.filmscape.util.MediaCard
import com.furkanhrmnc.filmscape.util.Snack
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchMoviesViewModel = koinInject(),
    navController: NavController,
) {
    val lazyGridState = rememberLazyGridState()
    val scrollToTop by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 3 } }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val searchMedias = viewModel.searchMedias.collectAsLazyPagingItems()
    val searchValue by viewModel.search.collectAsState()
    val error by viewModel.error.collectAsState()


    Snack(
        message = error,
        onDismissed = viewModel::onErrorConsumed,
        label = SNACK_LABEL_OK,
        snackBarHostState = snackbarHostState
    )

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
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = "Up")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                value = searchValue,
                onValueChange = viewModel::onSearch,
                onBack = navController::navigateUp,
                onClear = viewModel::onClear
            )
            LazyVerticalGrid(
                modifier = Modifier.padding(innerPadding),
                state = lazyGridState,
                columns = GridCells.Fixed(3)
            ) {

                when {
                    (searchMedias.loadState.refresh is LoadState.Error) -> {
                        viewModel.onError((searchMedias.loadState.refresh as LoadState.Error).error)
                    }

                    (searchMedias.loadState.append is LoadState.Error) -> {
                        viewModel.onError((searchMedias.loadState.refresh as LoadState.Error).error)
                    }
                }

                items(searchMedias.itemCount) { index ->
                    searchMedias[index]?.let { media ->
                        MediaCard(media = media)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
) {

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = value,
                onQueryChange = onValueChange,
                onSearch = {},
                expanded = expanded,
                placeholder = {},
                onExpandedChange = { isExpandedChange -> expanded = isExpandedChange },
                leadingIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = value.isNotBlank(),
                        exit = fadeOut(),
                        enter = fadeIn()
                    ) {
                        IconButton(onClick = onClear) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )
        },
        expanded = expanded,
        onExpandedChange = { isExpandedChange -> expanded = isExpandedChange },
    ) {}
}

