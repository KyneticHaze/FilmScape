package com.furkanhrmnc.filmscape.domain.model.details

import com.furkanhrmnc.filmscape.domain.model.Genre

data class MovieDetails(
    val id: Int,
    val backdropPath: String,
    val isAdult: Boolean,
    val budget: Int,
    val genres: List<Genre>,
    val originalCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val description: String,
    val popularity: Double,
    val link: String, // homepage
    val imdbId: String,
    val tagline: String,
    val status: String,
    val revenue: Int,
    val runtime: Int,
    val releaseDate: String,
    val voteAverage: Float,
    val voteCount: Int,
    val spokenLanguages: List<Language>,
    val productionCountries: List<Country>,
    val productionCompanies: List<Company>,
)