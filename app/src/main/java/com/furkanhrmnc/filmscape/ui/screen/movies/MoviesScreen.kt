package com.furkanhrmnc.filmscape.ui.screen.movies

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.util.MediaLazyColumnPaging
import com.furkanhrmnc.filmscape.util.MovieTabs
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel,
    navController: NavController,
) {

    val scope = rememberCoroutineScope()

    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMovies.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()

    val pagerState = rememberPagerState(pageCount = { MovieTabs.entries.size })
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Movies",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
            )
        },
    ) { scaffoldPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(scaffoldPadding)
        ) {
            Column {
                PrimaryTabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    MovieTabs.entries.forEachIndexed { index, movieTab ->
                        Tab(
                            selected = index == selectedTabIndex,
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = MaterialTheme.colorScheme.secondary,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(movieTab.ordinal)
                                }
                            },
                            text = {
                                Text(
                                    text = movieTab.category,
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedTabIndex)
                                        movieTab.selectedIcon else movieTab.unSelectedIcon,
                                    contentDescription = "Tab Icon"
                                )
                            }
                        )
                    }
                }

                HorizontalPager(state = pagerState) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        MediaLazyColumnPaging(
                            medias = when (pagerState.currentPage) {
                                0 -> popularMovies
                                1 -> topRatedMovies
                                2 -> upcomingMovies
                                3 -> nowPlayingMovies
                                else -> popularMovies
                            },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}