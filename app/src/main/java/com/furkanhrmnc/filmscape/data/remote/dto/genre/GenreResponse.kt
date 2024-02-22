package com.furkanhrmnc.filmscape.data.remote.dto.genre


import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>?
)