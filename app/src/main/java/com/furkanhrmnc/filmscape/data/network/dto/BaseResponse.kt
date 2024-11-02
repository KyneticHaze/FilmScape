package com.furkanhrmnc.filmscape.data.network.dto


import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<T>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?,
)

fun BaseResponse<MediaDTO>.toPagingMedia(): PaginatedData<Media> = PaginatedData(
    page = page ?: 1,
    results = results?.toMediaList() ?: emptyList(),
    totalPages = totalPages ?: 1,
    totalResults = totalResults ?: 1
)