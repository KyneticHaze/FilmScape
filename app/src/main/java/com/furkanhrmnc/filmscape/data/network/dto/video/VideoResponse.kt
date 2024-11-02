package com.furkanhrmnc.filmscape.data.network.dto.video


import com.furkanhrmnc.filmscape.domain.model.Video
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    val id: Int?,
    val results: List<VideoDTO>?,
)

fun VideoResponse.toVideoList(): List<Video> = results?.map { it.toVideo() } ?: emptyList()