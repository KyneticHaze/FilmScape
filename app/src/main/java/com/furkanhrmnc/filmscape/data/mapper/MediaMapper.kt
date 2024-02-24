package com.furkanhrmnc.filmscape.data.mapper

import com.furkanhrmnc.filmscape.common.Constants
import com.furkanhrmnc.filmscape.data.local.media.MediaEntity
import com.furkanhrmnc.filmscape.data.remote.dto.credit.Cast
import com.furkanhrmnc.filmscape.data.remote.dto.media.MediaDTO
import com.furkanhrmnc.filmscape.domain.model.CastModel
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
    movieDate = movieDate ?: Constants.unavailable,
    tvDate = tvDate ?: Constants.unavailable,
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
    movieDate = movieDate,
    tvDate = tvDate,
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
    movieDate = movieDate ?: Constants.unavailable,
    tvDate = tvDate ?: Constants.unavailable,
    posterPath = posterPath ?: Constants.unavailable,
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    popularity = popularity ?: 0.0,
    genreIds = genreIds?.joinToString(",") ?: "-1,-2"
)

fun Media.toMediaEntity(): MediaEntity = MediaEntity(
    backdropPath = backdropPath,
    adult = adult,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    movieDate = movieDate,
    title = title,
    voteCount = voteCount,
    voteAverage = voteAverage,
    id = id,
    tvDate = tvDate,
    genreIds = genreIds.joinToString(","),
    overview = overview,
    posterPath = posterPath,
    popularity = popularity,
    video = video
)

fun Cast.toCastModel(): CastModel = CastModel(
    originalName = originalName ?: Constants.unavailable,
    characterName = character ?: Constants.unavailable,
    image = profilePath ?: Constants.unavailable
)