package com.furkanhrmnc.filmscape.ui.util

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.domain.model.Detail
import com.furkanhrmnc.filmscape.ui.screen.detail.DetailUiState

@Composable
fun DetailSection(
    navController: NavController,
    mediaDetail: Detail,
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
        mediaDetail.let {
            Box(
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = mediaDetail.description,
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
                        contentDescription = mediaDetail.description,
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = mediaDetail.originalTitle,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = mediaDetail.voteAverage.toString(),
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
                    Text(text = "Vote: ${detailUiState.mediaDetail?.voteAverage.toString()}")
                }
            }
            Text(
                text = mediaDetail.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 10.dp),
                maxLines = 5
            )
            PosterList(posters = detailUiState.posterList, title = "Movie Photos")
            SimilarMovieList(media = detailUiState.similarMediaListModel, title = "Similar Movies", navController = navController)
        }
    }
}