package com.example.movieland.ui.screens.home_screen.main_screen.other_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieland.ui.screens.home_screen.components.MovieCard
import com.example.movieland.ui.screens.home_screen.main_screen.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PlayingScreen() {

    val viewModel = hiltViewModel<MainViewModel>()
    val playingState = viewModel.nowPlayingState.value

    val isShowBottomSheet = rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Box(modifier = Modifier.fillMaxSize(1f)) {
            playingState.movies.let {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "All Now Playing Movies",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = {
                            // Bottom Sheet
                            scope.launch { sheetState.expand() }
                            isShowBottomSheet.value = true

                        }) {
                            Icon(
                                imageVector = Icons.Rounded.List,
                                contentDescription = "Genre",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) {
                        items(it) { movie ->
                            MovieCard(
                                movie = movie,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                    if (isShowBottomSheet.value) {
                        ModalBottomSheet(
                            sheetState = sheetState,
                            onDismissRequest = {
                                scope.launch { sheetState.hide() }
                                isShowBottomSheet.value = false
                            }) {
                            Text(
                                text = "Movie by genre",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(8.dp)
                            )
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                GenreItem(label = "Action") {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenreItem(
    modifier: Modifier = Modifier,
    label: String,
    onFilterClick: () -> Unit
) {
    AssistChip(
        modifier = modifier,
        shape = CircleShape,
        onClick = onFilterClick,
        label = {
            Text(text = label)
        }
    )
}