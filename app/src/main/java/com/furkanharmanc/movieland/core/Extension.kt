package com.furkanharmanc.movieland.core

import com.furkanharmanc.movieland.data.remote.dto.detail.DetailResponse
import com.furkanharmanc.movieland.data.remote.dto.popular.MovieItem
import com.furkanharmanc.movieland.domain.model.DetailMovie
import com.furkanharmanc.movieland.domain.model.PopularMovie

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