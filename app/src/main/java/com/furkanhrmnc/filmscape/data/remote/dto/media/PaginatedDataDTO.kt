package com.furkanhrmnc.filmscape.data.remote.dto.media


import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.google.gson.annotations.SerializedName

data class PaginatedDataDTO(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<MovieDTO>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

fun PaginatedDataDTO.toModel(): PaginatedData<Movie> = PaginatedData(
    page = page ?: 1,
    results = results?.toListModel() ?: emptyList(),
    totalPages = totalPages ?: 1,
    totalResults = totalResults ?: 1
)