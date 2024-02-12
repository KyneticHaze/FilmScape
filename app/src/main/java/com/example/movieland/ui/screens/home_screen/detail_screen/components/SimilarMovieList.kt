package com.example.movieland.ui.screens.home_screen.detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieland.core.Routes
import com.example.movieland.domain.model.Media
import com.example.movieland.ui.screens.home_screen.main_screen.components.MediaCard

@Composable
fun SimilarMovieList(
    medias: List<Media>,
    title: String,
    navController: NavController
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(8.dp)
    )
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        items(medias.size) {
            val indexedMedia = medias[it]

            MediaCard(
                media = indexedMedia,
                navigateUp = {
                    navController.navigate(
                        "${Routes.Detail.route}?id=${indexedMedia.id}&type=${indexedMedia.mediaType}&category=${indexedMedia.category}"
                    )
                }
            )
        }
    }
}