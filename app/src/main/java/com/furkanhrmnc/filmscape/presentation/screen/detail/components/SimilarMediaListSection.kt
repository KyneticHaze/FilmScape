package com.furkanhrmnc.filmscape.presentation.screen.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.presentation.screen.detail.DetailUIState
import com.furkanhrmnc.filmscape.presentation.theme.filmScapeFont
import com.furkanhrmnc.filmscape.util.MediaCard


@Composable
fun SimilarMediaListSection(
    title: String,
    navController: NavController,
    detailUiState: DetailUIState
) {
    val similarMediaList = detailUiState.similarMediaList

    if (similarMediaList.isNotEmpty()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    fontFamily = filmScapeFont,
                    fontSize = 18.sp
                )
                Text(
                    modifier = Modifier
                        .alpha(.7f),
                    text = stringResource(id = R.string.see_all),
                    color = MaterialTheme.colorScheme.background,
                    fontFamily = filmScapeFont,
                    fontSize = 14.sp
                )
            }
            LazyRow {
                items(similarMediaList) {
                    MediaCard(
                        media = it,
                        navController = navController
                    )
                }
            }
        }
    }
}