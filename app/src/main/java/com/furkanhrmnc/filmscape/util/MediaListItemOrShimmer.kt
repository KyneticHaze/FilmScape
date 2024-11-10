package com.furkanhrmnc.filmscape.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.domain.model.Media

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MediaListItemOrShimmer(
    modifier: Modifier = Modifier,
    media: Media?,
    isLoading: Boolean,
    darkMode: Boolean,
    navController: NavController,
) {
    if (isLoading) {
        ShimmerListItem(modifier, darkMode = darkMode)
    } else {
        media?.let {
            MediaListItem(
                modifier = modifier,
                media = it,
                navController = navController
            )
        }
    }
}