package com.furkanhrmnc.filmscape.ui.screen.details

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.furkanhrmnc.filmscape.domain.model.toMedia
import com.furkanhrmnc.filmscape.navigation.Destinations
import com.furkanhrmnc.filmscape.util.Constants.getDisplayName
import com.furkanhrmnc.filmscape.util.DateFormatter
import com.furkanhrmnc.filmscape.util.MediaCard
import com.furkanhrmnc.filmscape.util.RatingBar
import com.furkanhrmnc.filmscape.util.Snack
import com.furkanhrmnc.filmscape.util.UiEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onNavigate: (UiEvent.NavigateTo) -> Unit,
) {
    val detailsUiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val user = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateTo -> onNavigate(event)
                is UiEvent.ShowSnackbar -> TODO()
            }
        }
    }

    Snack(
        message = detailsUiState.error,
        snackBarHostState = snackbarHostState,
        onDismissed = viewModel::onErrorConsumed
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(end = 16.dp),
                        text = detailsUiState.mediaDetail?.originalTitle ?: "",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            try {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(detailsUiState.mediaDetail?.homepage)
                                )
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                viewModel.onError(e)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Link,
                            contentDescription = "Go to Browser",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(
                        onClick = {
                            detailsUiState.mediaDetail?.let { mediaDetail ->
                                user?.let { user ->
                                    viewModel.onEvent(
                                        DetailsUiEvents.AddFavorite(
                                            uid = user.uid,
                                            media = mediaDetail.toMedia()
                                        )
                                    )
                                }
                                scope.launch {
                                    val snackbarResult = snackbarHostState.showSnackbar(
                                        message = "${getDisplayName(mediaDetail.toMedia())} Added to favorites",
                                        actionLabel = "Go To Favorite"
                                    )
                                    if (snackbarResult == SnackbarResult.ActionPerformed && detailsUiState.isFavorite) {
                                        onNavigate(UiEvent.NavigateTo(Destinations.FAVORITE.route))
                                    }
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite Media",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                if (detailsUiState.isLoading) CircularProgressIndicator()
                detailsUiState.mediaDetail?.let { mediaDetail ->
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .padding(6.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    if (detailsUiState.videoId.isNotEmpty()) {
                                        viewModel.onEvent(
                                            DetailsUiEvents.Navigate(route = "${Destinations.WATCH_VIDEO.route}?videoId=${detailsUiState.videoId}")
                                        )
                                    } else {
                                        Toast
                                            .makeText(
                                                context,
                                                "Video is not available!",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    }
                                }
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = mediaDetail.backdropPath,
                                contentDescription = mediaDetail.overview,
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 12.dp),
                                text = getDisplayName(media = mediaDetail.toMedia()),
                                color = MaterialTheme.colorScheme.background
                            )
                            Icon(
                                modifier = Modifier
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = CircleShape
                                    )
                                    .size(48.dp)
                                    .align(Alignment.Center),
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = "Play Video",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        OutlinedCard(modifier = Modifier.padding(6.dp)) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = getDisplayName(mediaDetail.toMedia()),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = DateFormatter.format(date = mediaDetail.firstAirDate.takeIf { it?.isNotBlank() == true }
                                        ?: mediaDetail.releaseDate ?: ""),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Medium
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = mediaDetail.voteAverage.toString(),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        fontWeight = FontWeight.Medium
                                    )
                                    RatingBar(
                                        Modifier.size(18.dp),
                                        starsColor = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Text(
                                    text = mediaDetail.overview ?: "",
                                    maxLines = 10,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            item {

                if (detailsUiState.isLoading) CircularProgressIndicator()

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Cast",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                LazyRow {
                    items(detailsUiState.movieCasts) { cast ->
                        TooltipBox(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(6.dp)
                                .clip(CircleShape),
                            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                            tooltip = {
                                PlainTooltip(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.primary
                                ) {
                                    Text(
                                        text = cast.name,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            },
                            state = rememberTooltipState()
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = cast.profilePath,
                                contentDescription = cast.name,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            item {
                if (detailsUiState.isLoading) CircularProgressIndicator()

                detailsUiState.mediaDetail?.let { mediaDetail ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "Recommendations",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            TextButton(
                                onClick = {
                                    viewModel.onEvent(
                                        DetailsUiEvents.Navigate(
                                            route = "${Destinations.SIMILAR.route}?id=${mediaDetail.id}"
                                        )
                                    )
                                }
                            ) {
                                Text(text = "See All", style = MaterialTheme.typography.titleSmall)
                            }
                        }
                        LazyRow {
                            items(detailsUiState.recommendedMovies) { media ->
                                MediaCard(
                                    modifier = Modifier.height(220.dp),
                                    media = media,
                                    onCLick = {
                                        viewModel.onEvent(
                                            DetailsUiEvents.Navigate(
                                                route = "${Destinations.DETAILS.route}?id=${media.id}&type=${media.type}"
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}