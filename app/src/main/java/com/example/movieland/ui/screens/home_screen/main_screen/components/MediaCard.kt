package com.example.movieland.ui.screens.home_screen.main_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.movieland.core.Routes
import com.example.movieland.data.remote.api.MediaApi.Companion.IMAGE_URL
import com.example.movieland.domain.model.Media
import com.example.movieland.ui.theme.Dimens
import okhttp3.Route

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    media: Media,
    navigateUp: () -> Unit
) {

    val imageUrl = "${IMAGE_URL}${media.posterPath}"
    val context = LocalContext.current

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )

    val imageState = imagePainter.state


    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.MEDIUM_RADIUS.dp))
            .clickable {
                navigateUp()
            }
    ) {

        if (imageState is AsyncImagePainter.State.Success) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = media.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(MaterialTheme.shapes.medium)
                )
            }
        }
        if (imageState is AsyncImagePainter.State.Error) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .scale(0.5f)
            )
        }
        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                imageVector = Icons.Rounded.Warning,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = media.title
            )
        }
    }
}