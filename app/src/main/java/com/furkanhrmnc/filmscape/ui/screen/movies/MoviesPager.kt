package com.furkanhrmnc.filmscape.ui.screen.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieUseCase
import com.furkanhrmnc.filmscape.util.Category

class MoviesPager(
    private val loadMovie: LoadMovieUseCase,
    private val config: PagingConfig = PagingConfig(pageSize = 20)
) {
    fun pagingDataFlow(category: Category) = Pager(config = config) {
        MoviesDataSource(category, loadMovie)
    }.flow
}