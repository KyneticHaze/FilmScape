package com.furkanhrmnc.filmscape.util

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.furkanhrmnc.filmscape.util.Constants.IMAGE_DURATION_MS

@Composable
fun ImageLauncher(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    data: Any?,
    contentScale: ContentScale = ContentScale.Crop,
    description: String = "Default Picture",
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(context)
            .data(data)
            .crossfade(IMAGE_DURATION_MS)
            .build()
    )

    Image(
        modifier = modifier,
        painter = painter,
        contentScale = contentScale,
        contentDescription = description
    )
}