package com.furkanhrmnc.filmscape.ui.screen.video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun WatchVideoScreen(
    videoKey: String,
    lifeCycleOwner: LifecycleOwner
) {

    val youtubeListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            youTubePlayer.loadVideo(
                videoId = videoKey,
                startSeconds = 0f
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { context ->
                YouTubePlayerView(context = context).apply {
                    lifeCycleOwner.lifecycle.addObserver(observer = this)
                    addYouTubePlayerListener(youTubePlayerListener = youtubeListener)
                }
            }
        )
    }
}