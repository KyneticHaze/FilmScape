package com.furkanhrmnc.filmscape.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.common.ApiTools
import com.furkanhrmnc.filmscape.ui.navigation.Routes
import com.furkanhrmnc.filmscape.ui.util.PosterList
import com.furkanhrmnc.filmscape.ui.util.SimilarMovieList
import com.furkanhrmnc.filmscape.domain.model.Media
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    media: Media,
    detailUiState: DetailUiState,
    onEvent: (DetailEvents) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetScaffoldState()
    var refreshing by remember { mutableStateOf(false) }
    val imageUrl = "${ApiTools.IMAGE_URL}${media.backdropPath}"

    fun refresh() = scope.launch {
        refreshing = true
        delay(1500L)
        onEvent(DetailEvents.Refresh)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    BottomSheetScaffold(
        modifier = Modifier
            .pullRefresh(refreshState),
        containerColor = MaterialTheme.colorScheme.background,
        scaffoldState = sheetState,
        sheetContent = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(8.dp)
                    .clickable {
                        // Add Movie ->
                        scope.launch {
                            sheetState.snackbarHostState.showSnackbar(
                                message = "Added Movie!",
                                actionLabel = "Added",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite the Movie"
                )
                Text(text = "Add Movie", style = MaterialTheme.typography.titleMedium)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share the Movie")
                Text(text = "Go to movie site", style = MaterialTheme.typography.titleMedium)
            }
        },
        sheetPeekHeight = 0.dp
    ) { paddingValues ->
        DetailSection(
            navController = navController,
            media = media,
            detailUiState = detailUiState,
            scaffoldPadding = paddingValues,
            imageUrl = imageUrl,
            expandSheet = {
                scope.launch {
                    sheetState.bottomSheetState.expand()
                }
            },
            navigateBack = {
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Detail.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Composable
fun DetailSection(
    navController: NavController,
    media: Media,
    detailUiState: DetailUiState,
    scaffoldPadding: PaddingValues,
    imageUrl: String,
    expandSheet: () -> Unit,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(scaffoldPadding)
            .verticalScroll(rememberScrollState())
    ) {
        media.let {
            Box(
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = media.overview,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                )
                IconButton(
                    onClick = expandSheet,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = -(15).dp, y = (20).dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "Tool"
                    )
                }
                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = (15).dp, y = (20).dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Get Back"
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(6.dp)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    modifier = Modifier
                        .offset(y = -(40).dp)
                        .padding(start = 8.dp)
                        .size(160.dp)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = media.overview,
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = media.originalTitle,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = media.voteAverage.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.stars),
                            contentDescription = "Rating",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(45.dp)
                        )
                    }
                    Text(text = "Vote: ${detailUiState.media?.voteAverage.toString()}")
                }
            }
            Text(
                text = media.overview,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 10.dp),
                maxLines = 5
            )
            PosterList(posters = detailUiState.posterList, title = "Movie Photos")
            SimilarMovieList(media = detailUiState.similarMediaListModel, title = "Similar Movies", navController = navController)
        }
    }
}