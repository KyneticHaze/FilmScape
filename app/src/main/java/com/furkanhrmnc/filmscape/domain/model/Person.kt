package com.furkanhrmnc.filmscape.domain.model

data class Person(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownFor: List<KnownFor>,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
)

data class KnownFor(
    val adult: Boolean,
    val backdropPath: String?,
    val firstAirDate: String?,
    val genreIds: List<Int>,
    val id: Int,
    val mediaType: String,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String?,
    val originalName: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
)
