package com.furkanhrmnc.filmscape.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.navigation.Destinations

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MediaLazyColumnPaging(
    modifier: Modifier = Modifier,
    medias: LazyPagingItems<Media>,
    navController: NavController,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            count = medias.itemCount,
            key = { id -> medias[id]?.id ?: id }
        ) { index ->
            medias[index]?.let { media ->
                MediaListItem(media = media, navController = navController)
            }
        }
    }
}

@Composable
fun MediaLazyGridPaging(
    modifier: Modifier = Modifier,
    searchMedias: LazyPagingItems<Media>,
    lazyGridState: LazyGridState,
    navController: NavController,
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        columns = GridCells.Fixed(3)
    ) {
        items(
            count = searchMedias.itemCount,
            key = { id -> searchMedias[id]?.id ?: id }
        ) { index ->
            searchMedias[index]?.let { media ->
                MediaCard(
                    media = media,
                    onCLick = { navController.navigate("${Destinations.DETAILS.route}?id=${media.id}&type=${media.type}") }
                )
            }
        }
    }
}