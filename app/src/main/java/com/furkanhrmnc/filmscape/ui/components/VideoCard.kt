package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.furkanhrmnc.filmscape.domain.model.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    video: Video,
    lifecycleOwner: LifecycleOwner,
) {

    var isClicked by remember {
        mutableStateOf(false)
    }

    AndroidView(
        modifier = modifier
            .size(350.dp)
            .padding(horizontal = 6.dp)
            .background(Color.Yellow)
            .clickable {
                isClicked = !isClicked
            },
        factory = { context ->
            YouTubePlayerView(context = context).apply {

                this.enableAutomaticInitialization = isClicked

                lifecycleOwner.lifecycle.addObserver(observer = this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadVideo(video.key, 0f)
                    }
                })
            }
        })
}