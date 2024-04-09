package com.furkanhrmnc.filmscape.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import com.furkanhrmnc.filmscape.util.shimmerEffect

@Composable
fun ImagePlaceholder(
    imagePainter: AsyncImagePainter,
    description: String
) {

    val imageState = imagePainter.state

    if (imageState is AsyncImagePainter.State.Success) {
        Image(
            painter = imagePainter,
            contentDescription = description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }

    if (imageState is AsyncImagePainter.State.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect(darkMode = false)
        )
    }

    if (imageState is AsyncImagePainter.State.Error) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = description,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .alpha(.7f),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}