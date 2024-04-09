package com.furkanhrmnc.filmscape.presentation.screen.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.util.Constants
import com.furkanhrmnc.filmscape.presentation.screen.main.MainUIState

@Composable
fun MediaSectionOrShimmer(
    type: String,
    showShimmer: Boolean,
    navController: NavController,
    mainUIState: MainUIState
) {
    val title = when (type) {
        Constants.TRENDING -> {
            stringResource(id = R.string.trending_now)
        }

        Constants.MOVIES_SCREEN -> {
            stringResource(id = R.string.movies)
        }

        Constants.TV_SCREEN -> {
            stringResource(id = R.string.tv_series)
        }

        else -> {
            stringResource(id = R.string.top_rated)
        }
    }

    if (showShimmer) {
        ShimmerSection(
            modifier = Modifier
                .size(width = 150.dp, height = 220.dp)
                .padding(top = 20.dp, start = 16.dp, bottom = 12.dp),
            title = title,
            paddingEnd = 16.dp
        )
    } else {
        MediaSection(
            type = type,
            navController = navController,
            mainUIState = mainUIState,
            seeAllClick = {
                // ?
            }
        )
    }
}