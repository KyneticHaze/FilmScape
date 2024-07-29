package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.navigation.components.Routes
import com.furkanhrmnc.filmscape.ui.components.MovieCard
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchMoviesViewModel = koinInject(),
    navController: NavController,
) {
    val lazyGridState = rememberLazyGridState()
    val scrollToTop by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex > 3
        }
    }
    val scope = rememberCoroutineScope()
    val searchMovies = viewModel.searchMovies.collectAsLazyPagingItems()
    val searchValue by viewModel.search.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 20.dp),
                value = searchValue,
                onValueChange = viewModel::onSearch,
                onBack = navController::navigateUp,
                onClear = viewModel::onClear
            )
        },
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
        // Search Section?
        LazyVerticalGrid(
            modifier = Modifier.padding(innerPadding),
            state = lazyGridState,
            columns = GridCells.Fixed(3)
        ) {
            items(searchMovies.itemCount) { index ->
                searchMovies[index]?.let { movie ->
                    val context = LocalContext.current
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .crossfade(500)
                            .data(movie.posterPath)
                            .error(R.drawable.error)
                            .size(Size.ORIGINAL)
                            .build()
                    )
                    MovieCard(
                        modifier = Modifier.size(200.dp),
                        painter = painter,
                        title = movie.originalTitle,
                        onClick = { navController.navigate("${Routes.DETAILS.route}?id=${movie.id}") }
                    )
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
    SearchBar(
        modifier = modifier,
        query = value,
        onQueryChange = onValueChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        placeholder = { Text(text = "Search") },
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer),
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
        },
        content = {},
    )
}