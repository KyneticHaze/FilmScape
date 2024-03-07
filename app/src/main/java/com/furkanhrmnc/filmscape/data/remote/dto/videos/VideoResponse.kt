package com.furkanhrmnc.filmscape.data.remote.dto.videos


import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val videos: List<VideoDTO>?
)