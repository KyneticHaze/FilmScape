package com.furkanhrmnc.filmscape.data.mappers

import com.furkanhrmnc.filmscape.common.Constants
import com.furkanhrmnc.filmscape.data.local.MediaEntity
import com.furkanhrmnc.filmscape.data.remote.dto.media.MediaDTO
import com.furkanhrmnc.filmscape.domain.model.Media

fun MediaDTO.toMedia(
): Media = Media(
    adult = adult ?: false,
    backdropPath = backdropPath ?: Constants.unavailable,
    genreIds = genreIds ?: emptyList(),
    id = id ?: 1,
    originalLanguage = originalLanguage ?: Constants.unavailable,
    originalTitle = originalTitle ?: Constants.unavailable,
    overview = overview ?: Constants.unavailable,
    popularity = popularity ?: 0.0,
    posterPath = posterPath ?: Constants.unavailable,
    releaseDate = movieDate ?: Constants.unavailable,
    title = title ?: Constants.unavailable,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    video = video ?: false
)

fun MediaEntity.toMedia(): Media = Media(
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

fun MediaDTO.toMediaEntity(): MediaEntity = MediaEntity(
    adult = adult ?: false,
    backdropPath = backdropPath ?: Constants.unavailable,
    id = id ?: 1,
    overview = overview ?: Constants.unavailable,
    originalLanguage = originalLanguage ?: Constants.unavailable,
    originalTitle = originalTitle ?: Constants.unavailable,
    title = title ?: Constants.unavailable,
    releaseDate = movieDate ?: Constants.unavailable,
    posterPath = posterPath ?: Constants.unavailable,
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    firstAirDate = movieDate ?: Constants.unavailable,
    popularity = popularity ?: 0.0,
    genreIds = genreIds?.joinToString(",") ?: "-1,-2"
)

fun Media.toMediaEntity(): MediaEntity = MediaEntity(
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