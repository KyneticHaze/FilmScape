package com.example.movieland.data.remote.dto.media


import com.google.gson.annotations.SerializedName

data class MediaResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val medias: List<MediaDTO>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)