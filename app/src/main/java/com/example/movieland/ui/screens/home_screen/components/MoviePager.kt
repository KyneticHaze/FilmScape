package com.example.movieland.ui.screens.home_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieland.data.remote.dto.commonDto.MovieDTO
import com.example.movieland.ui.screens.home_screen.util.imagePath
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviePager(
    movies: List<MovieDTO>
) {
    val pagerState = rememberPagerState {
        movies.size
    }

    HorizontalPager(state = pagerState) { i ->
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .height(280.dp)
        ) {
            AsyncImage(
                model = imagePath(movies[i].posterPath.orEmpty()),
                contentDescription = movies[i].overview,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(1f)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(movies.size) { indicator ->
            val color =
                if (pagerState.currentPage == indicator) MaterialTheme.colorScheme.tertiary else Color.Gray
            val size = if (pagerState.currentPage == indicator) 18.dp else 14.dp
            Box(
                modifier = Modifier
                    .size(size)
                    .height(14.dp)
                    .padding(3.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}