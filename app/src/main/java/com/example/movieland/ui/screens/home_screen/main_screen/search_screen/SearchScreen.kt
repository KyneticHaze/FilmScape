package com.example.movieland.ui.screens.home_screen.main_screen.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieland.ui.navigation.Screens
import com.example.movieland.ui.screens.home_screen.components.MovieCard
import com.example.movieland.ui.screens.home_screen.components.SearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController
) {

    val viewModel = hiltViewModel<SearchViewModel>()
    val moviesState = viewModel.movieState.value
    var searchQuery = viewModel.searchQuery

    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            // Genre
            Text(text = "Genre")
        },
        sheetPeekHeight = 0.dp,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(8.dp)
                )
                IconButton(onClick = {
                    scope.launch {
                        sheetState.bottomSheetState.expand()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "List to Genre",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            SearchBar(
                text = searchQuery,
                onTextChange = {
                    viewModel.searchMovie(it, 1)
                },
                clearText = {
                    viewModel.clearSearch()
                }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(6.dp)
            ) {
                items(moviesState.movies) {
                    MovieCard(
                        movie = it,
                        modifier = Modifier.padding(6.dp)
                    ) {
                        navController.navigate(Screens.Detail.screen)
                    }
                }
            }
        }
    }
}