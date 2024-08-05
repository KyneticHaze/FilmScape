package com.furkanhrmnc.filmscape.ui.screen.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieUseCase
import com.furkanhrmnc.filmscape.util.Category
import kotlinx.coroutines.flow.Flow

class MoviesPager(
    private val loadMovie: LoadMovieUseCase,
    private val config: PagingConfig = PagingConfig(pageSize = 20),
) {
    fun pagingDataFlow(category: Category): Flow<PagingData<Movie>> = Pager(config = config) {
        MoviesDataSource(category, loadMovie)
    }.flow
}