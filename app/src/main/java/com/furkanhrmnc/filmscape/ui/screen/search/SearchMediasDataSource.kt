package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Response
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class SearchMediasDataSource(
    private val type: String,
    private val query: String,
    private val repo: MediaRepository,
) : PagingSource<Int, Media>() {
    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.run {
                prevKey?.plus(1) ?: nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        return if (query.isEmpty()) {
            LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        } else {
            runCatching {
                loadPage(type = type, page = params.key ?: 1)
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

    private fun PaginatedData<Media>.prevKey(): Int? =
        if (page == 1) null else page.minus(1)

    private fun PaginatedData<Media>.nextKey(): Int? =
        if (page >= totalPages) null else page.plus(1)

    private suspend fun loadPage(type: String, page: Int): PaginatedData<Media> {
        return repo.searchMovieOrTv(
            type = type,
            query = query,
            page = page
        )
            .onEach { if (it is Response.Failure) throw it.throwable }
            .map { (it as Response.Success).data }
            .first()
    }
}