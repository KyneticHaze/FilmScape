package com.furkanhrmnc.filmscape.presentation.screen.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.util.Screens
import com.furkanhrmnc.filmscape.presentation.screen.main.MainUIState
import com.furkanhrmnc.filmscape.presentation.theme.filmScapeFont
import com.furkanhrmnc.filmscape.util.MediaCard
import com.furkanhrmnc.filmscape.util.TitleText

@Composable
fun MediaSection(
    type: String,
    navController: NavController,
    mainUIState: MainUIState,
    seeAllClick: () -> Unit = {}
) {
    val title = when (type) {
        Screens.TRENDING -> {
            stringResource(id = R.string.trending_now)
        }

        Screens.MOVIES_SCREEN -> {
            stringResource(id = R.string.movies)
        }

        Screens.TV_SCREEN -> {
            stringResource(id = R.string.tv_series)
        }

        else -> {
            stringResource(id = R.string.top_rated)
        }
    }

    val mediaList = when (type) {
        Screens.TRENDING -> {
            mainUIState.trendingAllList.take(10)
        }

        Screens.MOVIES_SCREEN -> {
            mainUIState.moviesList.take(10)
        }

        Screens.TV_SCREEN -> {
            mainUIState.tvSeriesList.take(10)
        }

        else -> {
            mainUIState.topRatedMoviesList.take(10)
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            TitleText(text = title)
            Text(
                modifier = Modifier
                    .clickable { seeAllClick() },
                text = stringResource(id = R.string.see_all),
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontFamily = filmScapeFont,
                fontSize = 14.sp
            )
        }

        LazyRow(
            content = {
                items(mediaList) { media ->
                    MediaCard(
                        movie = media,
                        navController = navController,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
        )
    }
}