package com.furkanhrmnc.filmscape.domain.model

import com.furkanhrmnc.filmscape.util.MediaType

data class Media(
    val id: Int = 0,

    val type: MediaType = MediaType.MOVIE, // TV or Movie

    val adult: Boolean = false,
    val backdropPath: String = "",
    val genreIds: List<Int> = emptyList(),
    val originalLanguage: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String? = null,
    val firstAirDate: String? = null,
    val originCountry: List<String>? = emptyList(),
    val originalName: String? = null,
    val name: String? = null,
    val originalTitle: String? = null,
    val title: String? = null,
    val voteCount: Long = 0,
    val voteAverage: Float = 0f,
)