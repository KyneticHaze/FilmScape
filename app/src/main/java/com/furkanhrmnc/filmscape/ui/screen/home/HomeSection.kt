package com.furkanhrmnc.filmscape.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.common.Constants
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState
import com.furkanhrmnc.filmscape.ui.util.MediaSectionOrShimmer
import com.furkanhrmnc.filmscape.ui.util.sharedComponents.SwipeSection
import com.furkanhrmnc.filmscape.ui.util.sharedComponents.TitleText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    mainUIState: MainUIState,
    navController: NavController,
    pullRefresh: PullRefreshState,
    paddingValues: PaddingValues,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .pullRefresh(pullRefresh),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
        ) {
            MediaSectionOrShimmer(
                type = Constants.TRENDING,
                showShimmer = mainUIState.trendingAllList.isEmpty(),
                navController = navController,
                mainUIState = mainUIState
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (mainUIState.specialList.isEmpty()) {
                TitleText(text = stringResource(id = R.string.special))
            } else {
                SwipeSection(
                    type = stringResource(id = R.string.special),
                    navController = navController,
                    mainUIState = mainUIState
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            MediaSectionOrShimmer(
                type = Constants.MOVIES_SCREEN,
                showShimmer = mainUIState.moviesList.isEmpty(),
                navController = navController,
                mainUIState = mainUIState
            )

            Spacer(modifier = Modifier.height(8.dp))

            MediaSectionOrShimmer(
                type = Constants.TV_SCREEN,
                showShimmer = mainUIState.tvSeriesList.isEmpty(),
                navController = navController,
                mainUIState = mainUIState
            )
        }
    }
}