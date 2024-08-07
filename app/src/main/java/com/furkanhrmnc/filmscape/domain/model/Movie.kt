package com.furkanhrmnc.filmscape.domain.model

data class Movie(
    val isAdult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val description: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteCount: Long,
    val voteAverage: Float,
)