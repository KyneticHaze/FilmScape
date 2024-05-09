package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.navigation.components.Routes
import com.furkanhrmnc.filmscape.ui.components.AppBottomBar
import com.furkanhrmnc.filmscape.ui.components.AppTopBar
import com.furkanhrmnc.filmscape.ui.components.MoviesSection
import com.furkanhrmnc.filmscape.ui.components.SomethingWentWrong
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.categoryResId
import com.furkanhrmnc.filmscape.util.getAllMoviesState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainUiState: MainUiState,
    navController: NavController
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                appTitle = stringResource(R.string.app_name),
                isSearchInAppBar = true
            )
        },
        bottomBar = {
            AppBottomBar(
                navController = navController
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { scaffoldPadding ->

        MainContent(
            contentPadding = scaffoldPadding,
            mainUiState = mainUiState,
            navController = navController,
            onError = { SomethingWentWrong(error = it, snackbarHostState = snackbarHostState) }
        )
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(2.dp),
    mainUiState: MainUiState,
    navController: NavController,
    onError: @Composable (throwable: Throwable) -> Unit
) {


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(contentPadding)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(Category.entries) { category ->
                MoviesSection(
                    modifier = Modifier.wrapContentSize(),
                    title = stringResource(id = category.categoryResId()),
                    moviesState = mainUiState.getAllMoviesState(category),
                    onMovieClick = { movie -> navController.navigate("${Routes.DETAILS.route}?id=${movie.id}") },
                    onMore = { navController.navigate("${Routes.MOVIES.route}?category=${category}") }
                )
            }
        }

        /// MainScreen'de bir hata oluştuğunda bu kod bloğu çalışacak ve içindeki composable aktif olacak.
        mainUiState.error?.run {
            onError(this)
        }
    }
}