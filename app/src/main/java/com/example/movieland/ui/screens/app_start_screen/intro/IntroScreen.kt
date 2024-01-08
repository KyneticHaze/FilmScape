package com.example.movieland.ui.screens.app_start_screen.intro

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.movieland.ui.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreen(
    navController: NavController, viewModel: IntroViewModel = hiltViewModel()
) {

    val introState = viewModel.introState.value
    val pagerState = rememberPagerState {
        introState.nowPlayingMovies.size
    }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        introState.nowPlayingMovies.let { movies ->
            HorizontalPager(
                state = pagerState
            ) { index ->
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    model = movies[index].poster,
                    contentDescription = movies[index].description
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> LoadingImage()
                        is AsyncImagePainter.State.Error -> ErrorImage()
                        else -> SubcomposeAsyncImageContent()
                    }
                }
            }
            Box(
                modifier = Modifier
                    .offset(y = -(100).dp)
                    .fillMaxWidth(.5f)
                    .clip(RoundedCornerShape(100))
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = .6f)
                    )
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                IconButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage - 1,
                            animationSpec = tween(700)
                        )
                    }
                }, modifier = Modifier.align(Alignment.BottomStart)) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                IconButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage + 1,
                            animationSpec = tween(700)
                        )
                    }
                }, modifier = Modifier.align(Alignment.BottomEnd)) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next")
                }
            }
            Button(
                onClick = {
                    navController.navigate(Routes.Auth.route) {
                        popUpTo(Routes.AppStart.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(width = 160.dp, height = 50.dp)
                    .offset(x = -(8).dp, y = -(15).dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Let's Login", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun LoadingImage() {
    LinearProgressIndicator(
        strokeCap = StrokeCap.Butt, trackColor = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun ErrorImage() {
    Icon(imageVector = Icons.Default.Warning, contentDescription = "Error")
}