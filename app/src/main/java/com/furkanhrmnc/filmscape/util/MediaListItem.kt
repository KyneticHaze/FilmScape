package com.furkanhrmnc.filmscape.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.navigation.Destinations
import com.furkanhrmnc.filmscape.util.Constants.getDisplayName

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MediaListItem(
    modifier: Modifier = Modifier,
    media: Media,
    navController: NavController,
) {
    ListItem(
        modifier = modifier
            .clickable { navController.navigate("${Destinations.DETAILS.route}?id=${media.id}&type=${media.type}") },
        leadingContent = {
            Card {
                AsyncImage(
                    model = media.posterPath,
                    contentDescription = media.overview,
                )
            }
        },
        headlineContent = {
            Text(
                text = getDisplayName(media),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
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
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium
                    )
                    RatingBar(
                        modifier = Modifier.size(18.dp),
                        rating = media.voteAverage.toDouble(),
                        starsColor = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = media.popularity.toString().take(4),
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (media.adult) "18+" else "13+",
                    color = if (media.adult) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = media.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        overlineContent = {
            Text(
                text = DateFormatter.format(date = media.firstAirDate.takeIf { it?.isNotBlank() == true }
                    ?: media.releaseDate ?: ""
                ),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
    )
}

@Composable
fun ShimmerListItem(
    modifier: Modifier = Modifier,
    darkMode: Boolean = false,
) {
    ListItem(
        modifier = modifier.shimmerEffect(darkMode),
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .shimmerEffect(darkMode)
                    .background(
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.medium
                    )
            )
        },
        headlineContent = {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(0.6f)
                    .shimmerEffect(darkMode)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            )
        },
        supportingContent = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth(0.4f)
                        .shimmerEffect(darkMode)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth(0.2f)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                        .shimmerEffect(darkMode)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )
            }
        }
    )
}