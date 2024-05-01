package com.furkanhrmnc.filmscape.domain.model

data class PagingMovie<T>(
    val page: Int,
    val results: List<T>,
    val totalPages: Int,
    val totalResults: Int
)
