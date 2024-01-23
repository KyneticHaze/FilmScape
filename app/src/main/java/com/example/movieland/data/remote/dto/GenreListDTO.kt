package com.example.movieland.data.remote.dto


import com.example.movieland.domain.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreListDTO(
    @SerializedName("genres")
    val genres: List<Genre>?
)