package com.example.movieland.core

import com.example.movieland.data.remote.dto.detail.DetailResponse
import com.example.movieland.data.remote.dto.detail.ProductionCompany
import com.example.movieland.data.remote.dto.popular.MovieItem
import com.example.movieland.domain.model.DetailMovie
import com.example.movieland.domain.model.PopularMovie

object Extension {
    private fun imagePath(path: String?): String {
        return "https://image.tmdb.org/t/p/w500$path"
    }

    fun MovieItem.toMovie(): PopularMovie = PopularMovie(
        title = title,
        image = imagePath(posterPath),
        desc = overview,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        id = id
    )

    fun DetailResponse.toDetail(): DetailMovie = DetailMovie(
        title = title,
        image = imagePath(posterPath),
        desc = overview,
        voteAverage = voteAverage,
        voteCount = voteCount,
        isAdult = adult,
        originLang = originalLanguage,
        productionCompanies = productionCompanies,
        releaseDate = releaseDate,
        popularity = popularity,
        id = id
    )
}