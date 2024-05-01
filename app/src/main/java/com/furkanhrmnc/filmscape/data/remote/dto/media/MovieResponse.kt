package com.furkanhrmnc.filmscape.data.remote.dto.media


import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<MovieDTO>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

fun MovieResponse.toPagingMovie(): PagingMovie<Movie> = PagingMovie(
    page = page ?: 1,
    results = results?.toModelList() ?: emptyList(),
    totalPages = totalPages ?: 1,
    totalResults = totalResults ?: 1
)