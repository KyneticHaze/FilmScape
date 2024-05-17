package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.furkanhrmnc.filmscape.domain.usecase.LoadRecommendationMoviesUseCase

class SimilarMoviesPager(
    movieId: Int,
    loadRecommendationMoviesUseCase: LoadRecommendationMoviesUseCase,
    config: PagingConfig = PagingConfig(pageSize = 20),
) {
    val similarPagingDataFlow = Pager(config = config) {
        SimilarMoviesDataSource(
            movieId = movieId,
            loadRecommendationMoviesUseCase = loadRecommendationMoviesUseCase
        )
    }.flow
}