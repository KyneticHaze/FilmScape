package com.furkanhrmnc.filmscape.ui.util.sharedComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.common.Constants
import com.furkanhrmnc.filmscape.ui.navigation.Routes
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState
import com.furkanhrmnc.filmscape.ui.theme.filmScapeFont

@Composable
fun MediaSection(
    type: String,
    navController: NavController,
    mainUIState: MainUIState,
    seeAllClick: () -> Unit = {}
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

    val mediaList = when (type) {
        Constants.TRENDING -> {
            mainUIState.trendingAllList.take(10)
        }

        Constants.MOVIES_SCREEN -> {
            mainUIState.moviesList.take(10)
        }

        Constants.TV_SCREEN -> {
            mainUIState.tvSeriesList.take(10)
        }

        else -> {
            mainUIState.topRatedMoviesList.take(10)
        }
    }

    Column {
        Row {
            TitleText(text = title)
            Text(
                modifier = Modifier
                    .clickable { seeAllClick() },
                text = stringResource(id = R.string.see_all),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontFamily = filmScapeFont,
                fontSize = 14.sp
            )
        }

        LazyRow(
            content = {
                items(mediaList) { media ->
                    MediaCard(
                        media = media,
                        navController = navController
                    )
                }
            }
        )
    }
}