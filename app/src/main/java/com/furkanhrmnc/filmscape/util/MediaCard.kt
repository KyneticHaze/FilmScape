package com.furkanhrmnc.filmscape.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.furkanhrmnc.filmscape.domain.model.Media

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    media: Media,
    isShimmer: Boolean = false,
    onCLick: () -> Unit,
) {

    if (isShimmer) {
        Box(
            modifier = modifier
                .size(160.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect()
        )
    }

    Card(
        modifier = modifier
            .size(160.dp)
            .padding(8.dp),
        onClick = onCLick
    ) {
        ImageLauncher(
            modifier = Modifier.fillMaxSize(),
            data = media.posterPath
        )
    }
}