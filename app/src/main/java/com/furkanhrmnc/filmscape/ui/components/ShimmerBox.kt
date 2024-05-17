package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.furkanhrmnc.filmscape.util.shimmerEffect

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    boxShape: Shape = RoundedCornerShape(10.dp),
) {
    Spacer(
        modifier = modifier
            .size(size)
            .clip(boxShape)
            .shimmerEffect()
    )
}