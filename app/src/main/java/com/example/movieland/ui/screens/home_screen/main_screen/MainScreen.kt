package com.example.movieland.ui.screens.home_screen.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.movieland.R
import com.example.movieland.data.remote.dto.commonDto.MovieDTO
import com.example.movieland.data.remote.dto.image.Poster
import com.example.movieland.ui.navigation.Screens
import com.example.movieland.ui.screens.app_start_screen.intro.ErrorImage
import com.example.movieland.ui.screens.app_start_screen.intro.LoadingImage
import com.example.movieland.ui.screens.home_screen.components.MovieCard
import com.example.movieland.ui.screens.home_screen.components.MovieList
import com.example.movieland.ui.screens.home_screen.components.MoviePager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateMovieDetails: (Int) -> Unit
) {

    val nowPlayingState = viewModel.nowPlayingState.value
    val upcomingState = viewModel.upcomingState.value
    val popularState = viewModel.popularState.value
    val topRatedState = viewModel.topRatedState.value

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
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
                    navController.navigate(Screens.Search.screen)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Search, contentDescription = "Movie Search"
                    )
                }
                IconButton(onClick = {
                    navController.navigate(Screens.Profile.screen)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "Account"
                    )
                }
            })
        }) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .verticalScroll(rememberScrollState())
        ) {
            MovieList(movies = nowPlayingState.movies,
                title = "Now Playing Movies",
                onNavigateMovieTopic = {
                    onNavigateMovieDetails(it)
                },
                onNavigateMovieAll = {
                    // All Movies
                    navController.navigate(Screens.Playing.screen)
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
                    navController.navigate(Screens.Upcoming.screen)
                })
            MovieList(movies = popularState.movies,
                title = "Popular Movies",
                onNavigateMovieTopic = {
                    onNavigateMovieDetails(it)
                },
                onNavigateMovieAll = {
                    navController.navigate(Screens.Popular.screen)
                })
            MovieList(movies = topRatedState.movies,
                title = "TopRated Movies",
                onNavigateMovieTopic = {
                    onNavigateMovieDetails(it)
                },
                onNavigateMovieAll = {
                    navController.navigate(Screens.TopRated.screen)
                })
        }
    }
}