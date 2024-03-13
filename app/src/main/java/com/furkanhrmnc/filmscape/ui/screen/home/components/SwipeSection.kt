package com.furkanhrmnc.filmscape.ui.screen.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState
import com.furkanhrmnc.filmscape.ui.util.sharedComponents.TitleText

@Composable
fun SwipeSection(
    type: String,
    navController: NavController,
    mainUIState: MainUIState
) {
    Column {
        TitleText(text = type, modifier = Modifier.padding(horizontal = 8.dp))
        SwipePager(
            mediaList = mainUIState.specialList.take(8),
            navController = navController
        )
    }
}