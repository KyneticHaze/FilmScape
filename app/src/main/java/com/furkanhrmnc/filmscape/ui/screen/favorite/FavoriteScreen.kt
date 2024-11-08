package com.furkanhrmnc.filmscape.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.furkanhrmnc.filmscape.util.Constants.getDisplayName
import com.furkanhrmnc.filmscape.util.Snack
import com.furkanhrmnc.filmscape.util.SwipeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Snack(
        message = uiState.error,
        snackBarHostState = snackbarHostState,
        onDismissed = viewModel::onErrorConsumed
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favorite Medias",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(
                count = uiState.favorites.size,
                key = { id -> uiState.favorites[id].id }
            ) { index ->
                val favoriteMedia = uiState.favorites[index]
                SwipeCard(
                    modifier = Modifier.animateItem(),
                    onDelete = { viewModel.deleteMediaFromFavorites(mediaId = favoriteMedia.id.toString()) }
                ) {
                    OutlinedCard(
                        modifier = Modifier.padding(6.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        ListItem(
                            leadingContent = {
                                Card(modifier = Modifier.size(100.dp)) {
                                    AsyncImage(
                                        modifier = Modifier.fillMaxSize(),
                                        model = favoriteMedia.posterPath,
                                        contentDescription = favoriteMedia.overview,
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            },
                            headlineContent = {
                                Text(
                                    text = getDisplayName(favoriteMedia),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = favoriteMedia.overview,
                                    maxLines = 3,
                                    style = MaterialTheme.typography.bodyMedium,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}