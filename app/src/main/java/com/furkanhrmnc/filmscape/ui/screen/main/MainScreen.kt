package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.usecase.ViewState
import com.furkanhrmnc.filmscape.ui.components.BottomBar
import com.furkanhrmnc.filmscape.ui.components.TopBar
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.MovieCard
import com.furkanhrmnc.filmscape.util.categoryResId
import com.furkanhrmnc.filmscape.util.moviesState

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainUIState: MainUIState,
    navController: NavController
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { TopBar() },
        bottomBar = { BottomBar(navController) }
    ) { scaffoldPadding ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(Category.entries) {category ->
                    MoviesSection(
                        title = stringResource(id = category.categoryResId()),
                        moviesState = mainUIState.moviesState(category),
                        onMovieDetail = {},
                        onMore = {}
                    )
                }
            }
        }
    }
}

@Composable
fun MoviesSection(
    modifier: Modifier = Modifier,
    title: String,
    moviesState: ViewState<List<Movie>>,
    onMovieDetail: (movie: Movie) -> Unit,
    onMore: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title)
            IconButton(
                onClick = onMore
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = title)
            }
        }
        when(moviesState) {
            is ViewState.Failure -> TODO()
            ViewState.Loading -> TODO()
            is ViewState.Success -> if (moviesState.data.isNotEmpty()) {
                Movies(movies = moviesState.data)
            }
        }
    }
}

@Composable
fun Movies(
    movies: List<Movie>
) {
    LazyRow {
        items(movies) {
            MovieCard(
                movie = it
            )
        }
    }
}