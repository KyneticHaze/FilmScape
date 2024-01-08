package com.example.movieland.data.remote.dto.playing_upComing


import com.example.movieland.data.remote.dto.commonDto.MovieDTO
import com.google.gson.annotations.SerializedName

data class PlayUpComingResponse(
    @SerializedName("dates")
    val dates: Dates?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val movies: List<MovieDTO>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)