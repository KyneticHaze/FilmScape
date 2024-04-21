package com.furkanhrmnc.filmscape.data.remote.dto.media


import com.furkanhrmnc.filmscape.domain.model.Movie
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
    val vote: Int?,
    @SerializedName("vote_count")
    val rating: Double?,
)

fun List<MovieDTO>.toListModel(): List<Movie> = map(MovieDTO::toMovie)

fun MovieDTO.toMovie(): Movie = Movie(
    adult = adult ?: false,
    backdropPath = backdropPath ?: "",
    genreIds = genreIds ?: emptyList(),
    id = id ?: 1,
    originalLanguage = originalLanguage ?: "",
    originalTitle = originalTitle ?: "",
    overview = overview ?: "",
    popularity = popularity ?: 0.0,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    rating = rating ?: 0.0,
    vote = vote ?: 0
)