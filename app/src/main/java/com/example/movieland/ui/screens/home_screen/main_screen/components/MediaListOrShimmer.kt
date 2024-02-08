package com.example.movieland.ui.screens.home_screen.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieland.R
import com.example.movieland.core.Constants
import com.example.movieland.core.Routes
import com.example.movieland.core.shimmerEffect
import com.example.movieland.domain.model.Media
import com.example.movieland.ui.screens.home_screen.main_screen.MainUIState
import com.example.movieland.ui.theme.Dimens
import com.example.movieland.ui.theme.customFont
import com.example.movieland.ui.theme.fonts

@Composable
fun MediaListOrShimmer(
    type: String,
    showShimmer: Boolean,
    mainUIState: MainUIState
) {

    val title = when (type) {
        Constants.TRENDING_SCREEN -> {
            stringResource(R.string.trending_now)
        }

        Constants.TV_SCREEN -> {
            stringResource(R.string.tv_series)
        }

        else -> stringResource(R.string.top_rated)
    }



    if (showShimmer) {
        ShimmerSection(
            title = title,
            modifier = Modifier
                .size(
                    width = 150.dp,
                    height = 200.dp
                )
                .padding(
                    top = 20.dp,
                    start = 16.dp,
                    bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    } else {
        MediaSection(
            type = type,
            mainUIState = mainUIState
        )
    }
}

@Composable
fun ShimmerSection(
    title: String,
    modifier: Modifier,
    paddingEnd: Dp
) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 22.dp),
            fontFamily = customFont,
            fontSize = 20.sp
        )
        LazyRow {
            items(10) {
                Box(
                    modifier = modifier
                        .padding(end = if (it == 9) paddingEnd else 0.dp)
                        .clip(RoundedCornerShape(Dimens.MEDIUM_RADIUS.dp))
                        .shimmerEffect(false)
                )
            }
        }
    }
}

@Composable
fun MediaSection(
    type: String,
    mainUIState: MainUIState
) {

    val title = when (type) {
        Constants.TRENDING_SCREEN -> {
            stringResource(R.string.trending_now)
        }

        Constants.TV_SCREEN -> {
            stringResource(R.string.tv_series)
        }

        else -> stringResource(R.string.top_rated)
    }

    val mediaList = when (type) {
        Constants.TRENDING_SCREEN -> {
            mainUIState.trendingAllMediaList.take(10)
        }

        Constants.TV_SCREEN -> {
            mainUIState.popularTvSeriesList.take(10)
        }

        else -> mainUIState.nowPlayingMediaList.take(10)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(vertical = 6.dp, horizontal = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
    }
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        items(mediaList.size) {

            val indexedMedia = mediaList[it]
            var paddingEnd = 0.dp

            // Sondaki öğeye gelince sağ padding'i arttır.
            if (it == mediaList.size - 1) {
                paddingEnd = 16.dp
            }

            MediaCard(
                media = indexedMedia,
                modifier = Modifier
                    .size(height = 200.dp, width = 150.dp)
                    .padding(
                        start = 16.dp,
                        end = paddingEnd
                    ),
            ) {
                "${Routes.Detail.route}?id=${indexedMedia.id}"
            }
        }
    }
}