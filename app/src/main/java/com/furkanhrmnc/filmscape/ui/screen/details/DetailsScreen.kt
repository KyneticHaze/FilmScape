package com.furkanhrmnc.filmscape.ui.screen.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.domain.model.Video
import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import com.furkanhrmnc.filmscape.navigation.components.Routes
import com.furkanhrmnc.filmscape.ui.components.MoviesSection
import com.furkanhrmnc.filmscape.ui.components.RatingBar
import com.furkanhrmnc.filmscape.ui.components.VideoCard
import com.furkanhrmnc.filmscape.util.Date
import com.furkanhrmnc.filmscape.util.ViewState
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(
    movieId: Int,
    navController: NavController,
) {
    val viewModel: DetailsViewModel = koinInject<DetailsViewModel> { parametersOf(movieId) }
    val detailsUiState by viewModel.detailsUiState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        detailsLazyItem(
            detailViewState = detailsUiState.movieDetails,
            onError = viewModel::onError
        )
        item {
            VideoSection(detailsUiState = detailsUiState)
        }
        item {
            MoviesSection(
                moviesState = detailsUiState.recommendedMovies,
                title = stringResource(id = R.string.recommendations),
                onMovieClick = { movie ->
                    navController.navigate("${Routes.DETAILS.route}?id=${movie.id}")
                },
                onMore = {
                    navController.navigate("${Routes.SIMILAR.route}?id=$movieId")
                }
            )
        }
    }
}

fun LazyListScope.detailsLazyItem(
    detailViewState: ViewState<MovieDetails>,
    onError: (Throwable) -> Unit,
) {
    when (detailViewState) {
        is ViewState.Failure -> item {
            LaunchedEffect(key1 = detailViewState.throwable) {
                detailViewState.throwable?.run(onError)
            }
        }

        ViewState.Loading -> item {
            CircularProgressIndicator(Modifier.padding(12.dp))
        }

        is ViewState.Success -> item {
            Details(details = detailViewState.data)
        }
    }
}

@Composable
fun Details(
    modifier: Modifier = Modifier,
    details: MovieDetails,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        BackdropSection(details = details)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            PosterSection(details = details)
            Spacer(modifier = Modifier.width(12.dp))
            InfoSection(details = details)
        }
    }
    OverviewSection(details = details)
}

@Composable
fun BackdropSection(
    modifier: Modifier = Modifier,
    details: MovieDetails,
) = with(details) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).crossfade(durationMillis = 400).data(backdropPath)
            .build()
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painter,
                contentDescription = description,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun PosterSection(
    modifier: Modifier = Modifier,
    details: MovieDetails,
) = with(details) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).crossfade(durationMillis = 400).data(posterPath)
            .build()
    )
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(200.dp))
        Card(
            modifier = Modifier
                .size(width = 180.dp, height = 250.dp)
                .padding(start = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painter,
                    contentDescription = description,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun InfoSection(
    modifier: Modifier = Modifier,
    details: MovieDetails,
) = with(details) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(260.dp))
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = originalTitle,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBar(starsModifier = Modifier.size(18.dp), rating = voteAverage.toDouble().div(2))
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = voteAverage.toString().take(3),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Date.format(releaseDate),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 6.dp),
                text = if (isAdult) "18+" else "-12",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun OverviewSection(
    modifier: Modifier = Modifier,
    details: MovieDetails,
) = with(details) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            text = "\"${tagline}\"",
            style = MaterialTheme.typography.titleMedium,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            text = stringResource(id = R.string.overview),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = description,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun VideoSection(
    modifier: Modifier = Modifier,
    detailsUiState: DetailsUiState,
) {
    Column(
        modifier = modifier.background(Color.Red),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = stringResource(id = R.string.videos),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        VideoLazyRow(videoViewState = detailsUiState.movieVideos)
    }
}


@Composable
fun VideoLazyRow(
    modifier: Modifier = Modifier,
    videoViewState: ViewState<List<Video>>,
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    when (videoViewState) {
        is ViewState.Failure -> Box(modifier = modifier.fillMaxWidth()) {
            Text(
                text = videoViewState.throwable?.message ?: "",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        ViewState.Loading -> CircularProgressIndicator()
        is ViewState.Success -> {
            LazyRow {
                items(
                    items = videoViewState.data,
                    key = { it.id }
                ) { video ->
                    VideoCard(video = video, lifecycleOwner = lifecycleOwner)
                }
            }
        }
    }
}