package com.furkanhrmnc.filmscape.data.remote.dto.movie


import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.util.ApiConfig
import com.google.gson.annotations.SerializedName

data class MovieDTO(
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
    @SerializedName("vote_count")
    val voteCount: Long?,
)

fun List<MovieDTO>.toModelList(): List<Movie> = map(MovieDTO::toMovie)

fun MovieDTO.toMovie(): Movie = Movie(
    isAdult = adult ?: false,
    backdropPath = "${ApiConfig.IMAGE_URL}$backdropPath" ?: "",
    genreIds = genreIds ?: emptyList(),
    id = id ?: 1,
    originalLanguage = originalLanguage ?: "",
    originalTitle = originalTitle ?: "",
    description = overview ?: "",
    popularity = popularity ?: 0.0,
    posterPath = "${ApiConfig.IMAGE_URL}$posterPath" ?: "",
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    voteCount = voteCount ?: 0,
    voteAverage = voteAverage ?: 0f
)