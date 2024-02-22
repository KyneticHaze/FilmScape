package com.furkanhrmnc.filmscape.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.furkanhrmnc.filmscape.common.Constants
import com.furkanhrmnc.filmscape.ui.util.MediaListOrShimmer
import com.furkanhrmnc.filmscape.ui.util.MovieTopBar

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