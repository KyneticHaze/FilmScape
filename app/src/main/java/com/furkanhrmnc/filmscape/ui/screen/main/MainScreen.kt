package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.navigation.Routes
import com.furkanhrmnc.filmscape.ui.components.AppBottomBar
import com.furkanhrmnc.filmscape.ui.components.SomethingWentWrong
import com.furkanhrmnc.filmscape.ui.components.MoviesSection
import com.furkanhrmnc.filmscape.ui.components.AppTopBar
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.categoryResId
import com.furkanhrmnc.filmscape.util.moviesState

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {

    val mainUIState by viewModel.mainUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar() },
        bottomBar = { AppBottomBar(navController) },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }) { scaffoldPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(Category.entries) { category ->
                    MoviesSection(
                        modifier = Modifier.fillMaxSize(),
                        title = stringResource(id = category.categoryResId()),
                        moviesState = mainUIState.moviesState(category),
                        onMovieClick = { movie -> navController.navigate("${Routes.Detail.route}?id=${movie.id}") },
                        onMore = { navController.navigate("${Routes.Movies.route}?category=${category}") })
                }
            }
            mainUIState.error?.run {
                SomethingWentWrong(
                    error = this,
                    snackbarHostState = snackbarHostState,
                    onDismissed = viewModel::onErrorConsumed
                )
            }
        }
    }
}