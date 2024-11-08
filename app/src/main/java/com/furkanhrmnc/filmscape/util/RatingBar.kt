package com.furkanhrmnc.filmscape.util

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.furkanhrmnc.filmscape.util.Constants.STAR_COLOR

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = STAR_COLOR,
) {

    val halfStar = (rating % 1) != 0.0

    Row {
        for (index in 1..stars) {
            Icon(
                modifier = modifier, imageVector = if (index <= rating) {
                    Icons.Rounded.Star
                } else {
                    if (halfStar) {
                        Icons.AutoMirrored.Rounded.StarHalf
                    } else {
                        Icons.Rounded.StarOutline
                    }
                }, contentDescription = "Rating", tint = starsColor
            )
        }
    }
}