package com.furkanhrmnc.filmscape.ui.screen.detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.common.ApiTools.IMAGE_URL
import com.furkanhrmnc.filmscape.common.Constants
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.ui.theme.filmScapeFont
import com.furkanhrmnc.filmscape.ui.util.VideoSection
import com.furkanhrmnc.filmscape.ui.util.sharedComponents.ImagePlaceholder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.floor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    media: Media,
    detailUiState: DetailUIState,
    onEvent: (DetailUIEvents) -> Unit
) {

    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    fun refresh() = scope.launch {
        isRefreshing = true
        delay(1000L)
        onEvent(DetailUIEvents.Refresh)
        isRefreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = ::refresh)
    val context = LocalContext.current

    val imageUrl = "${IMAGE_URL}${media.backdropPath}"

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(1000)
            .size(Size.ORIGINAL)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pullRefresh(refreshState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                VideoSection(
                    navController = navController,
                    detailUiState = detailUiState,
                    media = media,
                    imagePainter = imagePainter,
                    onEvent = onEvent
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    PosterSection(
                        media = media,
                        context = context
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    InfoSection(
                        media = media,
                        detailUiState = detailUiState
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                OverviewSection(media = media)
            }
        }
    }
}

@Composable
fun OverviewSection(
    media: Media
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = buildString {
                append("\"")
                media.tagline
                append("\"")
            },
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.background,
            fontStyle = FontStyle.Italic,
            fontFamily = filmScapeFont,
            lineHeight = 12.sp
        )
    }
}

@Composable
fun InfoSection(
    media: Media,
    detailUiState: DetailUIState
) {

    val releaseDate =
        if (media.releaseDate != Constants.unavailable) media.releaseDate.take(4) else ""
    val adultLimit = if (media.adult) "+18" else "-12"

    Column {
        Spacer(modifier = Modifier.height(180.dp))
        Text(
            text = media.originalTitle,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            fontFamily = filmScapeFont,
            fontSize = 19.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBar(
                starsModifier = Modifier.size(18.dp),
                rating = media.voteAverage.div(2)
            )
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = media.voteAverage.toString().take(3),
                fontFamily = filmScapeFont,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.background
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = releaseDate,
                color = MaterialTheme.colorScheme.background,
                fontSize = 15.sp,
                fontFamily = filmScapeFont
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(6.dp)),
                text = adultLimit,
                color = MaterialTheme.colorScheme.background,
                fontFamily = filmScapeFont,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.padding(end = 6.dp),
            text = detailUiState.time,
            fontFamily = filmScapeFont,
            color = MaterialTheme.colorScheme.background,
            lineHeight = 14.sp
        )
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starsModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow
) {

    // floor() -> Bir sayıyı aşağı yuvarlar. 5.67 = 5
    // ceil() -> Bir sayıyı yukarı yuvarlar. 6.22 = 7

    val filledStars = floor(rating).toInt()
    val unFilledStars = (stars - ceil(rating)).toInt()

    // rem(1) -> sayıyı 1'e böler
    val isThereHalfStar = !(rating.rem(1).equals(0.0))

    Box(
        modifier = modifier
    ) {
        repeat(filledStars) {
            Icon(
                modifier = starsModifier,
                painter = painterResource(id = R.drawable.ic_filled_star),
                contentDescription = "filled stars",
                tint = starsColor
            )
        }
        if (isThereHalfStar) {
            Icon(
                modifier = starsModifier,
                painter = painterResource(id = R.drawable.ic_half_star),
                contentDescription = "half stars",
                tint = starsColor
            )
        }
        repeat(unFilledStars) {
            Icon(
                modifier = starsModifier,
                painter = painterResource(id = R.drawable.ic_empty_star),
                contentDescription = "unfilled stars",
                tint = starsColor
            )
        }
    }
}

@Composable
fun PosterSection(
    media: Media,
    context: Context
) {
    val posterUrl = "${IMAGE_URL}${media.posterPath}"

    val posterPainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(posterUrl)
            .crossfade(1000)
            .size(Size.ORIGINAL)
            .build()
    )

    Column {
        Spacer(modifier = Modifier.height(150.dp))
        Card(
            modifier = Modifier
                .size(
                    width = 180.dp,
                    height = 250.dp
                )
                .padding(start = 16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ImagePlaceholder(
                    imagePainter = posterPainter,
                    description = media.originalTitle
                )
            }
        }
    }
}
