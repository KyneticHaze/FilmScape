package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    painter: AsyncImagePainter,
    isFailure: Boolean = false,
    movieDesc: String? = null,
    shape: Shape = RoundedCornerShape(6.dp),
    scale: ContentScale = ContentScale.Fit,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(170.dp)
            .padding(4.dp)
            .clip(shape)
            .clickable { onClick() }
    ) {
        if (isFailure) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error"
            )
        } else {
            Image(
                painter = painter,
                contentDescription = movieDesc,
                contentScale = scale
            )
        }
    }
}