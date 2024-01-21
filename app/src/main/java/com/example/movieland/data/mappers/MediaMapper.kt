package com.example.movieland.data.mappers

import com.example.movieland.core.Constants
import com.example.movieland.data.remote.dto.movie.MediaDTO
import com.example.movieland.domain.model.Media

fun MediaDTO.toMedia() : Media {
    return Media(
        adult = adult ?: false,
        backdropPath = backdropPath ?: Constants.unavailable,
        genreIds = genreIds ?: emptyList(),
        id = id ?: 1,
        originalLanguage = originalLanguage ?: Constants.unavailable,
        originalTitle = originalTitle ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: Constants.unavailable,
        releaseDate = releaseDate ?: Constants.unavailable,
        title = title ?: Constants.unavailable,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        runtime = null,
        status = null,
        tagline = null
    )
}