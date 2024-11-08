package com.furkanhrmnc.filmscape.util

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.furkanhrmnc.filmscape.util.Constants.SHIMMER_ANIMATION_LABEL
import com.furkanhrmnc.filmscape.util.Constants.SHIMMER_DURATION_MS
import com.furkanhrmnc.filmscape.util.Constants.SHIMMER_INFINITE_LABEL

/**
 * Kullanıcı arayüzünde gösterilecek yapıların henüz hazır olmadığı durumlarda parıldama efektini verecek bir [Modifier] döndürecektir.
 */
fun Modifier.shimmerEffect(darkMode: Boolean = false) = composed {

    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = SHIMMER_INFINITE_LABEL)
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = SHIMMER_DURATION_MS,
                easing = LinearEasing
            )
        ),
        label = SHIMMER_ANIMATION_LABEL
    )

    val shimmerColors = if (darkMode) {
        listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.background
        )
    } else {
        listOf(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.primaryContainer
        )
    }

    background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(
                x = startOffsetX,
                y = 0f
            ),
            end = Offset(
                x = startOffsetX + size.width.toFloat(),
                y = size.height.toFloat()
            )
        )
    ).onGloballyPositioned { layoutCoordinate ->
        size = layoutCoordinate.size
    }
}