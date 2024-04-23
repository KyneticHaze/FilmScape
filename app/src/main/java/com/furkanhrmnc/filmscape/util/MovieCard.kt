package com.furkanhrmnc.filmscape.util

import androidx.compose.foundation.Image
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.navigation.Routes

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
) {

    val navController = rememberNavController()
    val movieDetail = { navController.navigate("${Routes.Detail.route}?id=${movie.id}") }
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

    Card(
        onClick = movieDetail
    ) {
        when(imageState) {
            AsyncImagePainter.State.Empty -> TODO()
            is AsyncImagePainter.State.Error -> TODO()
            is AsyncImagePainter.State.Loading -> TODO()
            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = imagePainter,
                    contentDescription = movie.overview
                )
            }
        }
    }

}