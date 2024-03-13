package com.furkanhrmnc.filmscape.ui.screen.detail.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.ui.navigation.Routes
import com.furkanhrmnc.filmscape.ui.screen.detail.DetailUIEvents
import com.furkanhrmnc.filmscape.ui.screen.detail.DetailUIState
import com.furkanhrmnc.filmscape.ui.util.sharedComponents.ImagePlaceholder

@Composable
fun VideoSection(
    navController: NavController,
    detailUiState: DetailUIState,
    media: Media,
    imagePainter: AsyncImagePainter,
    onEvent: (DetailUIEvents) -> Unit
) {

    val context = LocalContext.current
    val notFoundVideoToast = Toast.makeText(
        context,
        context.getString(R.string.not_found_video),
        Toast.LENGTH_SHORT
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable {
                if (detailUiState.videoList.isNotEmpty()) {
                    onEvent(DetailUIEvents.WatchVideo)
                    navController.navigate("${Routes.Video.route}?videoKey=${detailUiState.videoKey}")
                } else {
                    notFoundVideoToast.show()
                }
            },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ImagePlaceholder(
                imagePainter = imagePainter,
                description = media.originalTitle
            )
        }
    }
}