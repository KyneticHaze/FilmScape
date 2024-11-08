package com.furkanhrmnc.filmscape.data.network.dto.video


import com.furkanhrmnc.filmscape.domain.model.Video
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    val id: Int? = null,
    val results: List<VideoDTO?>? = null,
)

fun VideoResponse.toVideoList(): List<Video> = results?.mapNotNull { it?.toVideo() } ?: emptyList()