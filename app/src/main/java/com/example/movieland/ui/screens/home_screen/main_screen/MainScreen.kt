package com.example.movieland.ui.screens.home_screen.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.movieland.core.Constants
import com.example.movieland.ui.screens.home_screen.main_screen.components.MediaListOrShimmer
import com.example.movieland.ui.screens.home_screen.main_screen.components.MovieTopBar

@Composable
fun MainScreen(
    mainUIState: MainUIState
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { MovieTopBar() }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .verticalScroll(rememberScrollState())
        ) {
            MediaListOrShimmer(
                type = Constants.TRENDING_SCREEN,
                showShimmer = mainUIState.trendingAllMediaListModel.isEmpty(),
                mainUIState = mainUIState
            )
            MediaListOrShimmer(
                type = Constants.TV_SCREEN,
                showShimmer = mainUIState.topRatedMediaListModel.isEmpty(),
                mainUIState = mainUIState
            )
        }
    }
}