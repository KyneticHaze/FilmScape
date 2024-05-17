package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    painter: AsyncImagePainter,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    shape: Shape = RoundedCornerShape(10.dp),
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(6.dp)
            .clip(shape)
            .clickable { onClick() }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Movie Image",
            contentScale = ContentScale.Crop
        )
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
                .align(Alignment.BottomStart),
            style = titleStyle,
            color = MaterialTheme.colorScheme.background,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            softWrap = true,
            overflow = TextOverflow.Ellipsis
        )
    }
}