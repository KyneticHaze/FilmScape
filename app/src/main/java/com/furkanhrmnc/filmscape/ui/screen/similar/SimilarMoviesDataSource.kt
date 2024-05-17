package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.usecase.LoadRecommendationMoviesUseCase
import com.furkanhrmnc.filmscape.domain.usecase.RecommendationMovieParams
import com.furkanhrmnc.filmscape.util.NetworkOperation
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class SimilarMoviesDataSource(
    private val movieId: Int,
    private val loadRecommendationMoviesUseCase: LoadRecommendationMoviesUseCase,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.run {
                prevKey?.plus(1) ?: nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> =
        runCatching { loadPage(page = params.key ?: 1) }
            .fold(
                onSuccess = {
                    LoadResult.Page(
                        data = it.results,
                        prevKey = it.prevKey(),
                        nextKey = it.nextKey()
                    )
                },
                onFailure = {
                    LoadResult.Error(it)
                }
            )

    private fun PagingMovie<Movie>.prevKey(): Int? =
        if (page == 1) null else page.minus(1)

    private fun PagingMovie<Movie>.nextKey(): Int? =
        if (page >= totalPages) null else page.plus(1)


    private suspend fun loadPage(page: Int): PagingMovie<Movie> {
        return loadRecommendationMoviesUseCase(
            param = RecommendationMovieParams(
                id = movieId,
                page = page
            )
        )
            .filter { result -> result !is NetworkOperation.Loading }
            .onEach { result -> if (result is NetworkOperation.Failure) throw result.throwable }
            .map { result -> (result as NetworkOperation.Success).data }
            .first()
    }
}