package com.furkanhrmnc.filmscape.data.network.dto.person


import com.furkanhrmnc.filmscape.domain.model.KnownFor
import com.furkanhrmnc.filmscape.util.Constants.imageFormatter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KnownForDTO(
    val adult: Boolean?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int?>?,
    val id: Int?,
    @SerialName("media_type")
    val mediaType: String?,
    val name: String? = null,
    @SerialName("origin_country")
    val originCountry: List<String?>? = null,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    val overview: String?,
    val popularity: Double?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?,
)

fun KnownForDTO.toKnownFor(): KnownFor = KnownFor(
    adult = adult ?: false,
    backdropPath = imageFormatter(backdropPath),
    posterPath = imageFormatter(posterPath),
    firstAirDate = firstAirDate,
    genreIds = genreIds?.filterNotNull() ?: emptyList(),
    id = id ?: 0,
    mediaType = mediaType ?: "",
    name = name ?: "",
    originCountry = originCountry?.filterNotNull() ?: emptyList(),
    originalLanguage = originalLanguage ?: "",
    originalName = originalName ?: "",
    originalTitle = originalTitle ?: "",
    overview = overview ?: "",
    popularity = popularity ?: 0.0,
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0
)