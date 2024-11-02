package com.furkanhrmnc.filmscape.ui.screen.details

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.navigation.components.Destinations
import com.furkanhrmnc.filmscape.util.MediaCard
import com.furkanhrmnc.filmscape.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onNavigate: (UiEvent.NavigateTo) -> Unit,
) {
    val detailsUiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateTo -> onNavigate(event)
                is UiEvent.ShowSnackbar -> TODO()
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = detailsUiState.mediaDetail?.originalTitle ?: ""
                )
            })
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        Card(modifier = Modifier.size(width = 150.dp, height = 220.dp)) {
                            AsyncImage(
                                model = mediaDetail.posterPath,
                                contentDescription = mediaDetail.overview,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    RecommendationSection(
                        medias = detailsUiState.recommendedMedias,
                        onMore = { viewModel.onEvent(DetailUiEvent.Navigate("${Destinations.SIMILAR.route}?id=${viewModel.id}")) },
                        onClick = { id -> viewModel.onEvent(DetailUiEvent.Navigate("${Destinations.DETAILS.route}?id=$id")) }
                    )
                }
            }


        }
    }
}

@Composable
fun VideoSection(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    player = viewModel.player
                    viewModel.onEvent(
                        DetailUiEvent.PlayVideo(
                            videoId = uiState.videoKey ?: ""
                        )
                    )
                }
            }
        )
    }
}

@Composable
fun RecommendationSection(
    modifier: Modifier = Modifier,
    medias: List<Media>,
    onMore: () -> Unit,
    onClick: (Int) -> Unit,
) {
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
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            TextButton(onClick = onMore) {
                Text(text = "See All")
            }
        }
        LazyRow(modifier = modifier) {
            items(medias) { media ->
                MediaCard(
                    modifier = Modifier.height(240.dp),
                    media = media,
                    clickable = { id -> onClick(id) }
                )
            }
        }
    }
}