package com.furkanhrmnc.filmscape.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Swipe(
    item: T,
    content: (T)  -> Unit
) {

    var isRemovedItem by remember {
        mutableStateOf(false)
    }

    val animationDuration = 500

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemovedItem = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemovedItem) {
        if (isRemovedItem) {
            delay(animationDuration.toLong())
            // make something
        }
    }

    AnimatedVisibility(
        visible = !isRemovedItem
    ) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = { SwipeBackgroundContent(swipeToDismissBoxState = dismissState) },
            content = { content(item) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackgroundContent(
    swipeToDismissBoxState: SwipeToDismissBoxState
) {

    val color =
        if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart) Color.Red else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove Item")
    }
}