package com.furkanhrmnc.filmscape.ui.util

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
import com.furkanhrmnc.filmscape.ui.navigation.Routes
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.ui.util.sharedComponents.MediaCard

@Composable
fun SimilarMovieList(
    media: List<Media>,
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
        items(media.size) {
            val indexedMedia = media[it]

            MediaCard(
                media = indexedMedia,
                navigateUp = {
                    navController.navigate(
                        "${Routes.Detail.route}?id=${indexedMedia.id}"
                    )
                }
            )
        }
    }
}