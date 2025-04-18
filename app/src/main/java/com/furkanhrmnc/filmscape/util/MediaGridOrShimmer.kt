package com.furkanhrmnc.filmscape.util

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.navigation.Destinations

@Composable
fun MediaGridOrShimmer(
    modifier: Modifier = Modifier,
    mediaItems: LazyPagingItems<Media>?,
    isLoading: Boolean,
    lazyGridState: LazyGridState,
    navController: NavController,
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        columns = GridCells.Fixed(3)
    ) {

        if (isLoading) {
            items(15) {
                MediaCard(
                    media = Media(),
                    isShimmer = true,
                    onCLick = {}
                )
            }
        } else {
            mediaItems?.let { items ->
                items(
                    count = items.itemCount,
                    key = { index -> items[index]?.id ?: index }
                ) { index ->
                    items[index]?.let { media ->
                        MediaCard(
                            media = media,
                            isShimmer = false,
                            onCLick = {
                                navController.navigate("${Destinations.DETAILS.route}?id=${media.id}&type=${media.type}") {
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}