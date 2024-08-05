package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.util.Category

/**
 * Kategorilere göre sayfalanmış veriler için yazıldı.
 *
 * @author Furkan Harmancı
 */
data class CategoryMovieParams(
    val category: Category,
    val page: Int = 1
)

/**
 * ID bazlı sayfalanmış verileri kolaylaştırmak için yazıldı.
 *
 * @author Furkan Harmancı
 */
data class RecommendationMovieParams(
    val id: Int,
    val page : Int = 1
)

data class SearchParams(
    val query: String,
    val page: Int = 1
)