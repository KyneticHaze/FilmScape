package com.example.movieland.data.remote.dto.person

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val persons: List<PersonDTO>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)