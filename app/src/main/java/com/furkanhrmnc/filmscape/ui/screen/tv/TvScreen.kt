package com.furkanhrmnc.filmscape.ui.screen.tv

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
import com.furkanhrmnc.filmscape.util.Constants.TV_SERIES
import com.furkanhrmnc.filmscape.util.MediaLazyColumnPaging
import com.furkanhrmnc.filmscape.util.TvTabs
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvScreen(
    modifier: Modifier = Modifier,
    viewModel: TvViewModel,
    navController: NavController,
) {


    val popularTvSeries = viewModel.popularTvSeries.collectAsLazyPagingItems()
    val topRatedTvSeries = viewModel.topRatedTvSeries.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { TvTabs.entries.size })
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }


    Scaffold(
        modifier = modifier, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = TV_SERIES,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            Column {
                PrimaryTabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TvTabs.entries.forEachIndexed { index, tvTab ->
                        Tab(
                            selected = index == selectedTabIndex,
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = MaterialTheme.colorScheme.secondary,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(tvTab.ordinal)
                                }
                            },
                            text = {
                                Text(
                                    text = tvTab.category,
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedTabIndex)
                                        tvTab.selectedIcon else tvTab.unSelectedIcon,
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
                            medias = if (pagerState.currentPage == 0) popularTvSeries else topRatedTvSeries,
                            navController = navController
                        )
                    }
                }
            }

        }
    }
}