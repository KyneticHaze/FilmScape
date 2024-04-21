package com.furkanhrmnc.filmscape.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.navigation.Routes

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    navController: NavController
) {

    val imageUrl = "${ApiConfig.IMAGE_URL}${movie.posterPath}"
    val context = LocalContext.current

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .crossfade(1000)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )

    val imageState = imagePainter.state

    Box(
        modifier = modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                navController.navigate("${Routes.Detail.route}?id=${movie.id}")
            }
            .background(MaterialTheme.colorScheme.onBackground)
    ) {

        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                painter = imagePainter,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        if (imageState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)
                    .scale(0.5f)
            )
        }
        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center),
                imageVector = Icons.Rounded.Warning,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = movie.title
            )
        }
    }
}