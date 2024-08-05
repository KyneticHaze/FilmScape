package com.furkanhrmnc.filmscape.data.remote.dto.genre


import com.furkanhrmnc.filmscape.domain.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)

fun GenreDTO.toGenre(): Genre = Genre(
    id = id ?: 0,
    name = name ?: ""
)