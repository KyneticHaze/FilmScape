package com.furkanhrmnc.filmscape.data.remote.dto.videos


import com.furkanhrmnc.filmscape.domain.model.Video
import com.furkanhrmnc.filmscape.domain.model.Videos
import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val videos: List<VideoDTO>?
)

fun VideoResponse.toVideos(): Videos<Video> = Videos(
    id = id ?: 0,
    videos = videos?.map(VideoDTO::toVideo) ?: emptyList()
)