package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.util.MediaCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipePager(
    movieList: List<Movie>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val pagerState =
        rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) { movieList.size }

    val scope = rememberCoroutineScope()
    val swipeMillis = 3500L

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            key = {
                movieList[it].id ?: 0
            }
        ) { index ->

            LaunchedEffect(key1 = Unit) {
                while (true) {
                    delay(swipeMillis)
                    scope.launch {
                        if (pagerState.canScrollForward) {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1
                            )
                        } else {
                            pagerState.animateScrollToPage(
                                page = 0
                            )
                        }
                    }
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = modifier
                        .height(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    MediaCard(
                        modifier = Modifier.fillMaxSize(),
                        movie = movieList[index],
                        navController = navController
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        Color.White
                                    )
                                )
                            )
                            .padding(16.dp, 22.dp, 16.dp, 12.dp)
                            .align(Alignment.BottomStart)
                    ) {
                        Text(text = movieList[index].originalTitle)
                    }
                }
            }
        }
        Row(
            modifier = Modifier.padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IndicatorSection(
                totalDots = movieList.size,
                selectedIndex = pagerState.currentPage
            )
        }
    }
}