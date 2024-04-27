package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    painter: AsyncImagePainter,
    title: String,
    isFailure: Boolean = false,
    movieDesc: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.secondary,
    border: BorderStroke? = null,
    cardElevation: Dp = 3.dp,
    cardShape: Shape = RoundedCornerShape(6.dp),
    onTap: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onTap,
        border = border,
        shape = cardShape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
    ) {
        if (isFailure) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error"
            )
        } else {
            Image(
                painter = painter,
                contentDescription = movieDesc
            )
            Text(
                text = title,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}