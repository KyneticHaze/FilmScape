package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.PER_PAGE_COUNT

class SimilarMediasPager(
    private val repo: MediaRepository,
    private val config: PagingConfig = PagingConfig(pageSize = PER_PAGE_COUNT),
) {
    fun similarPagingDataFlow(type: String, id: Int) = Pager(config = config) {
        SimilarMediasPagingDataSource(
            type = type,
            movieId = id,
            repo = repo
        )
    }.flow
}