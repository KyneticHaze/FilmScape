package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.navigation.Destinations
import com.furkanhrmnc.filmscape.util.BottomNavigationBar
import com.furkanhrmnc.filmscape.util.Constants.CAROUSEL_MS
import com.furkanhrmnc.filmscape.util.Constants.MOVIES
import com.furkanhrmnc.filmscape.util.Constants.SEE_ALL
import com.furkanhrmnc.filmscape.util.Constants.TRENDING
import com.furkanhrmnc.filmscape.util.Constants.TV_SERIES
import com.furkanhrmnc.filmscape.util.MediaCard
import com.furkanhrmnc.filmscape.util.Snack
import com.furkanhrmnc.filmscape.util.shimmerEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavHostController,
) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { uiState.onTheAirCarousel.size }


    Snack(
        message = uiState.error,
        snackBarHostState = snackbarHostState,
        onDismissed = viewModel::onErrorConsumed
    )

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(CAROUSEL_MS)
            scope.launch {
                if (pagerState.canScrollForward) {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                } else {
                    pagerState.animateScrollToPage(0)
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Destinations.SETTINGS.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = padding
        ) {

            item {
                SingleMediaSection(
                    sectionTitle = TRENDING,
                    seeAll = { navController.navigate(Destinations.TRENDING.route) }
                ) {
                    LazyRow {
                        items(uiState.trendingMedias) { media ->
                            MediaCard(
                                modifier = Modifier.height(220.dp),
                                media = media,
                                isShimmer = uiState.isLoading,
                                onCLick = { navController.navigate("${Destinations.DETAILS.route}?id=${media.id}&type=${media.type}") }
                            )
                        }
                    }
                }
            }

            item {
                if (uiState.onTheAirCarousel.isNotEmpty()) {
                    Box(modifier = Modifier.wrapContentSize()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(horizontal = 12.dp),
                                text = "On The Air",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            HorizontalPager(
                                modifier = Modifier.padding(6.dp),
                                state = pagerState,
                                key = { uiState.onTheAirCarousel[it].id },
                                pageSize = PageSize.Fill
                            ) { index ->

                                if (uiState.isLoading) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(220.dp)
                                            .padding(8.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .shimmerEffect()
                                    )
                                }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(220.dp)
                                        .padding(8.dp)
                                ) {
                                    AsyncImage(
                                        modifier = Modifier.fillMaxSize(),
                                        model = uiState.onTheAirCarousel[index].backdropPath,
                                        contentDescription = uiState.onTheAirCarousel[index].overview,
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            LazyRow {
                                items(uiState.onTheAirCarousel.size) { index ->
                                    if (uiState.isLoading) {
                                        Box(
                                            modifier = Modifier
                                                .padding(horizontal = 3.dp)
                                                .size(9.dp)
                                                .clip(CircleShape)
                                                .background(color = MaterialTheme.colorScheme.primary)
                                                .shimmerEffect()
                                        )
                                    }
                                    if (index == pagerState.currentPage) {
                                        Box(
                                            modifier = Modifier
                                                .padding(horizontal = 3.dp)
                                                .size(9.dp)
                                                .clip(CircleShape)
                                                .background(color = MaterialTheme.colorScheme.primary)
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .padding(horizontal = 3.dp)
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(color = MaterialTheme.colorScheme.secondary)
                                        )
                                    }
                                    if (index != uiState.onTheAirCarousel.size - 1) {
                                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                SingleMediaSection(
                    sectionTitle = MOVIES,
                    seeAll = { navController.navigate(Destinations.MOVIES.route) }
                ) {
                    uiState.movie?.let {

                        if (uiState.isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .padding(6.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerEffect()
                            )
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .height(220.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxSize(),
                                model = it.backdropPath,
                                contentDescription = it.overview,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                SingleMediaSection(
                    sectionTitle = TV_SERIES,
                    seeAll = { navController.navigate(Destinations.TV.route) }
                ) {
                    uiState.tvSeries?.let {

                        if (uiState.isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .padding(6.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerEffect()
                            )
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .height(220.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = it.backdropPath,
                                    contentDescription = it.overview,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SingleMediaSection(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    seeAll: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(6.dp)
            .clickable { seeAll() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = sectionTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            TextButton(onClick = seeAll) {
                Text(
                    text = SEE_ALL,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        content()
    }
}