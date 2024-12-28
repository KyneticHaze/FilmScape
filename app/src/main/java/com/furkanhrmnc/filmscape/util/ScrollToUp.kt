package com.furkanhrmnc.filmscape.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.furkanhrmnc.filmscape.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScrollToUp(
    modifier: Modifier = Modifier,
    scrollToTop: Boolean,
    lazyGridState: LazyGridState,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    AnimatedVisibility(
        visible = scrollToTop,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FloatingActionButton(
            modifier = modifier,
            onClick = {
                scope.launch {
                    lazyGridState.animateScrollToItem(0)
                }
            }, containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowUpward,
                contentDescription = stringResource(id = R.string.up_to_screen)
            )
        }
    }
}