package com.example.movieland.ui.screens.home_screen.detail_screen

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.movieland.R
import com.example.movieland.data.remote.dto.commonDto.MovieDTO
import com.example.movieland.data.remote.dto.image.Poster
import com.example.movieland.ui.MainActivity
import com.example.movieland.ui.navigation.Screens
import com.example.movieland.ui.screens.app_start_screen.intro.ErrorImage
import com.example.movieland.ui.screens.app_start_screen.intro.LoadingImage
import com.example.movieland.ui.screens.home_screen.components.MovieCard
import com.example.movieland.ui.screens.home_screen.components.MovieList
import com.example.movieland.ui.screens.home_screen.detail_screen.components.CastList
import com.example.movieland.ui.screens.home_screen.detail_screen.components.PosterList
import com.example.movieland.ui.screens.home_screen.detail_screen.components.SimilarMovieList
import com.example.movieland.ui.screens.home_screen.util.imagePath
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController
) {

    val viewModel = hiltViewModel<DetailViewModel>()
    val detailState = viewModel.detailState.value
    val similarState = viewModel.similarState.value
    val imageState = viewModel.imageState.value
    val creditState = viewModel.creditState.value

    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetScaffoldState()
    val uriHandler = LocalUriHandler.current

    BottomSheetScaffold(
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
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(8.dp)
                    .clickable {
                        val uri = detailState.movieDetail?.homepage
                        uri?.let {
                            uriHandler.openUri(uri)
                        }
                    }) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share the Movie")
                Text(text = "Go to movie site", style = MaterialTheme.typography.titleMedium)
            }
        },
        sheetPeekHeight = 0.dp
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            detailState.movieDetail?.let { detail ->
                Box(
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    AsyncImage(
                        model = imagePath(detail.backdropPath.orEmpty()),
                        contentDescription = detail.overview,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .blur(.6.dp)
                            .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    )
                    IconButton(
                        onClick = {
                            scope.launch {
                                sheetState.bottomSheetState.expand()
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = -(15).dp, y = (20).dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.List, contentDescription = "Tool"
                        )
                    }
                    IconButton(
                        onClick = {
                            // Back
                            navController.navigate(Screens.Main.screen) {
                                popUpTo(Screens.Detail.screen) {
                                    inclusive = true
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = (15).dp, y = (20).dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, contentDescription = "Get Back"
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
                            model = imagePath(detail.posterPath.orEmpty()),
                            contentDescription = detail.overview,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = detail.originalTitle.orEmpty(),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = detail.voteAverage.toString(),
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
                        Text(text = "Vote: ${detail.voteCount.toString()}")
                        Text(text = "Revenue: $${detail.revenue}")
                    }
                }
                Text(
                    text = detail.overview.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    maxLines = 5
                )
                PosterList(posters = imageState.posters, title = "Movie Photos")
                CastList(casts = creditState.casts, title = "Actors")
                SimilarMovieList(movies = similarState.movies, title = "Similar Movies")
            }
        }
    }
}