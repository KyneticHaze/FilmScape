package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.usecase.LoadSearchMoviesUseCase
import com.furkanhrmnc.filmscape.domain.usecase.SearchParams
import com.furkanhrmnc.filmscape.util.NetworkOperation
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class SearchMoviesDataSource(
    private val query: String,
    private val loadSearchMoviesUseCase: LoadSearchMoviesUseCase,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.run {
                prevKey?.plus(1) ?: nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return if (query.isEmpty()) {
            LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        } else {
            runCatching {
                loadPage(params.key ?: 1)
            }.fold(
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
        }
    }

    private fun PagingMovie<Movie>.prevKey(): Int? =
        if (page == 1) null else page.minus(1)

    private fun PagingMovie<Movie>.nextKey(): Int? =
        if (page >= totalPages) null else page.plus(1)

    private suspend fun loadPage(page: Int): PagingMovie<Movie> {
        return loadSearchMoviesUseCase(param = SearchParams(query = query, page = page))
            .filter { it !is NetworkOperation.Loading }
            .onEach { if (it is NetworkOperation.Failure) throw it.throwable }
            .map { (it as NetworkOperation.Success).data }
            .first()
    }
}