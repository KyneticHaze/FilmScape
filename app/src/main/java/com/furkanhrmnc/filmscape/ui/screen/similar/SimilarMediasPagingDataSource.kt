package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Response
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class SimilarMediasPagingDataSource(
    private val type: String,
    private val movieId: Int,
    private val repo: MediaRepository,
) : PagingSource<Int, Media>() {
    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.run {
                prevKey?.plus(1) ?: nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> =
        runCatching {
            loadPage(
                type = type,
                page = params.key ?: 1
            )
        }
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

    private fun PaginatedData<Media>.prevKey(): Int? =
        if (page == 1) null else page.minus(1)

    private fun PaginatedData<Media>.nextKey(): Int? =
        if (page >= totalPages) null else page.plus(1)


    private suspend fun loadPage(
        type: String,
        page: Int,
    ): PaginatedData<Media> {
        return repo.getRecommendationsMovieOrTv(
            type = type,
            id = movieId,
            page = page
        )
            .onEach { result -> if (result is Response.Failure) throw result.throwable }
            .map { result -> (result as Response.Success).data }
            .first()
    }
}