package com.example.movieland.ui.screens.home_screen.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieland.R
import com.example.movieland.core.Constants
import com.example.movieland.core.Routes
import com.example.movieland.ui.screens.home_screen.main_screen.components.MovieList
import com.example.movieland.ui.screens.home_screen.main_screen.components.MoviePager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainUIState: MainUIState,
    onEvent: (MainUiEvent) -> Unit
) {

    var refreshing by remember {
        mutableStateOf(false)
    }

    val refreshScope = rememberCoroutineScope()

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(2000)
        onEvent(MainUiEvent.Refresh(Constants.HOME_SCREEN))
    }

    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    Scaffold(containerColor = MaterialTheme.colorScheme.background, topBar = {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_no_background),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = -(50).dp),
                    contentScale = ContentScale.Fit
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        ), actions = {
            IconButton(onClick = {
                // Movie Search
                navController.navigate(Routes.Search.route)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Search, contentDescription = "Movie Search"
                )
            }
        })
    }) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .verticalScroll(rememberScrollState())
                .pullRefresh(refreshState)
        ) {
            MovieList(movies = nowPlayingState.movies,
                title = "Now Playing Movies",
                onNavigateMovieTopic = {
                    onNavigateMovieDetails(it)
                },
                onNavigateMovieAll = {
                    // All Movies
                    navController.navigate(Routes.Playing.route)
                })
            Text(
                text = "On The Air",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
            )
            MoviePager(movies = popularState.movies)
            MovieList(movies = upcomingState.movies,
                title = "Up Coming Movies",
                onNavigateMovieTopic = {
                    onNavigateMovieDetails(it)
                },
                onNavigateMovieAll = {
                    navController.navigate(Routes.Upcoming.route)
                })
            MovieList(movies = popularState.movies,
                title = "Popular Movies",
                onNavigateMovieTopic = {
                    onNavigateMovieDetails(it)
                },
                onNavigateMovieAll = {
                    navController.navigate(Routes.Popular.route)
                })
            MovieList(movies = topRatedState.movies,
                title = "TopRated Movies",
                onNavigateMovieTopic = {
                    onNavigateMovieDetails(it)
                },
                onNavigateMovieAll = {
                    navController.navigate(Routes.TopRated.route)
                })
        }
    }
}