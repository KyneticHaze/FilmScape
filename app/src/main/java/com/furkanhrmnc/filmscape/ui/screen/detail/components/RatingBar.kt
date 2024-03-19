package com.furkanhrmnc.filmscape.ui.screen.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.furkanhrmnc.filmscape.R
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starsModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow
) {

    // floor() -> Bir sayıyı aşağı yuvarlar. 5.67 = 5
    // ceil() -> Bir sayıyı yukarı yuvarlar. 6.22 = 7

    val filledStars = floor(rating).toInt()
    val unFilledStars = (stars - ceil(rating)).toInt()

    // rem(1) -> sayıyı 1'e böler
    val isThereHalfStar = !(rating.rem(1).equals(0.0))

    Box(
        modifier = modifier
    ) {
        repeat(filledStars) {
            Icon(
                modifier = starsModifier,
                painter = painterResource(id = R.drawable.ic_filled_star),
                contentDescription = "filled stars",
                tint = starsColor
            )
        }
        if (isThereHalfStar) {
            Icon(
                modifier = starsModifier,
                painter = painterResource(id = R.drawable.ic_half_star),
                contentDescription = "half stars",
                tint = starsColor
            )
        }
        repeat(unFilledStars) {
            Icon(
                modifier = starsModifier,
                painter = painterResource(id = R.drawable.ic_empty_star),
                contentDescription = "unfilled stars",
                tint = starsColor
            )
        }
    }
}