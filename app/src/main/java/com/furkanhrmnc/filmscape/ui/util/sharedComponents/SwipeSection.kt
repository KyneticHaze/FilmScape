package com.furkanhrmnc.filmscape.ui.util.sharedComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState
import com.furkanhrmnc.filmscape.ui.util.SwipePager

@Composable
fun SwipeSection(
    type: String,
    navController: NavController,
    mainUIState: MainUIState
) {
    Column {
        TitleText(text = type)
        SwipePager(
            mediaList = mainUIState.specialList.take(7),
            navController = navController
        )
    }
}