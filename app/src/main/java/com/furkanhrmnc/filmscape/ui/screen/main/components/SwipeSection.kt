package com.furkanhrmnc.filmscape.ui.screen.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState

@Composable
fun SwipeSection(
    type: String,
    navController: NavController,
    mainUIState: MainUIState
) {
    Column {
        Text(text = type, modifier = Modifier.padding(horizontal = 8.dp))
        SwipePager(
            movieList = mainUIState.specialList.take(8),
            navController = navController
        )
    }
}