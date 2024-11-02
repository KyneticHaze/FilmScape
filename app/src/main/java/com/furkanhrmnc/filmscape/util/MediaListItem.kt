package com.furkanhrmnc.filmscape.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.navigation.components.Destinations

@Composable
fun MediaListItem(
    modifier: Modifier = Modifier,
    media: Media,
    navController: NavController,
) {

    ListItem(
        modifier = modifier.clickable { navController.navigate("${Destinations.DETAILS.route}?id=${media.id}") },
        leadingContent = {
            Card {
                AsyncImage(
                    model = media.posterPath,
                    contentDescription = media.description,
                )
            }
        },
        headlineContent = {
            Text(
                text = media.title ?: media.name ?: "",
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = media.voteAverage.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    RatingBar(
                        modifier = Modifier.size(18.dp),
                        rating = media.voteAverage.toDouble()
                    )
                }
                Text(text = media.popularity.toString().take(4))
                Text(
                    text = if (media.adult) "18+" else "13+",
                    color = if (media.adult) Color.Red else Color.Green,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = media.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        overlineContent = { Text(text = Date.format(media.releaseDate ?: "Not Found Date")) },
    )
}