package com.furkanhrmnc.filmscape.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.furkanhrmnc.filmscape.domain.model.Media

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    media: Media,
    clickable: (Int) -> Unit = {},
) {
    Card(
        modifier = modifier
            .size(160.dp)
            .padding(8.dp),
        onClick = { clickable(media.id) }
    ) {
        ImageLauncher(
            modifier = Modifier.fillMaxSize(),
            data = media.posterPath
        )
    }
}