package com.example.movieland.data.mappers

import com.example.movieland.core.Constants
import com.example.movieland.data.local.MediaEntity
import com.example.movieland.data.remote.dto.media.MediaDTO
import com.example.movieland.domain.model.Media

fun MediaDTO.toMedia(
): Media {
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
        video = video ?: false
    )
}

fun MediaEntity.toMedia(): Media {
    return Media(
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds.split(",").map { it.toInt() }, // "35,80"
        id = id,
        originalTitle = originalTitle,
        video = video,
    )
}

fun MediaDTO.toMediaEntity(): MediaEntity {
    return MediaEntity(
        adult = adult ?: false,
        backdropPath = backdropPath ?: Constants.unavailable,
        id = id ?: 1,
        overview = overview ?: Constants.unavailable,
        originalLanguage = originalLanguage ?: Constants.unavailable,
        originalTitle = originalTitle ?: Constants.unavailable,
        title = title ?: Constants.unavailable,
        releaseDate = releaseDate ?: Constants.unavailable,
        posterPath = posterPath ?: Constants.unavailable,
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        firstAirDate = releaseDate ?: Constants.unavailable,
        popularity = popularity ?: 0.0,
        genreIds = genreIds?.joinToString(",") ?: "-1,-2"
    )
}

fun Media.toMediaEntity(): MediaEntity {
    return MediaEntity(
        backdropPath = backdropPath,
        adult = adult,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        releaseDate = releaseDate,
        title = title,
        voteCount = voteCount,
        voteAverage = voteAverage,
        id = id,
        firstAirDate = releaseDate,
        genreIds = genreIds.joinToString(","),
        overview = overview,
        posterPath = posterPath,
        popularity = popularity,
        video = video
    )
}