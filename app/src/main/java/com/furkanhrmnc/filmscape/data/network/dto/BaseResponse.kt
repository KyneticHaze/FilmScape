package com.furkanhrmnc.filmscape.data.network.dto


import com.furkanhrmnc.filmscape.data.network.dto.person.PersonDTO
import com.furkanhrmnc.filmscape.data.network.dto.person.toPerson
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.model.Person
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val page: Int? = null,
    val results: List<T?>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null,
)

fun BaseResponse<MediaDTO>.toPagingMedia(): PaginatedData<Media> = PaginatedData(
    page = page ?: 1,
    results = results?.mapNotNull { it?.toMedia() } ?: emptyList(),
    totalPages = totalPages ?: 1,
    totalResults = totalResults ?: 1
)

fun BaseResponse<PersonDTO>.toPagingPerson(): PaginatedData<Person> = PaginatedData(
    page = page ?: 1,
    results = results?.mapNotNull { it?.toPerson() } ?: emptyList(),
    totalPages = totalPages ?: 1,
    totalResults = totalResults ?: 1
)