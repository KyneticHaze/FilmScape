package com.furkanhrmnc.filmscape.data.network.dto

import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.util.Constants.imageFormatter
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaDTO(
    val adult: Boolean? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,
    val id: Int? = null,
    val name: String? = null,
    val title: String? = null,
    @SerialName("origin_country")
    val originCountry: List<String>? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Long? = null,
)

fun MediaDTO.toMedia(): Media = Media(
    id = id ?: 0,
    adult = adult ?: false,
    backdropPath = imageFormatter(backdropPath),
    posterPath = imageFormatter(posterPath),
    genreIds = genreIds ?: emptyList(),
    originalLanguage = originalLanguage ?: "",
    title = title ?: "",
    name = name ?: "",
    overview = overview ?: "",
    popularity = popularity ?: 0.0,
    releaseDate = releaseDate ?: "",
    firstAirDate = firstAirDate ?: "",
    originCountry = originCountry ?: emptyList(),
    voteCount = voteCount ?: 0L,
    voteAverage = (voteAverage ?: 0.0).toFloat(),
    originalTitle = originalTitle ?: "",
    originalName = originalName ?: "",
    type = when {
        !releaseDate.isNullOrEmpty() -> MediaType.MOVIE
        !firstAirDate.isNullOrEmpty() -> MediaType.TV
        else -> MediaType.MOVIE
    }
)