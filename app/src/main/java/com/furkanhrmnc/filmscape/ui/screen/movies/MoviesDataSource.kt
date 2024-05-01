package com.furkanhrmnc.filmscape.ui.screen.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieUseCase
import com.furkanhrmnc.filmscape.domain.usecase.MovieDiff
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.NetworkOperation
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MoviesDataSource(
    private val category: Category,
    private val loadMovie: LoadMovieUseCase
):PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.run {
                prevKey?.plus(1) ?: nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return runCatching { loadPage(page = params.key ?: 1) }
            .fold(
                onSuccess = { result ->
                    LoadResult.Page(
                        data = result.results,
                        prevKey = result.moviePrevKey(),
                        nextKey = result.movieNextKey()
                    )
                },
                onFailure = { throwable ->
                    LoadResult.Error(
                        throwable = throwable
                    )
                }
            )
    }

    private fun PagingMovie<Movie>.moviePrevKey(): Int? =
        if (page == 1) null else page.minus(1)


    private fun PagingMovie<Movie>.movieNextKey(): Int? =
        if (page >= totalPages) null else page.plus(1)

    private suspend fun loadPage(page: Int): PagingMovie<Movie> {
        return loadMovie(input = MovieDiff(category, page))
            .filter { result -> result !is NetworkOperation.Loading }
            .onEach { result -> if (result is NetworkOperation.Failure) throw result.throwable }
            .map { result -> (result as NetworkOperation.Success).data }
            .first()
    }
}