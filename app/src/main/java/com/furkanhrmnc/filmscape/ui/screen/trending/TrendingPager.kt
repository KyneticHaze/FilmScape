package com.furkanhrmnc.filmscape.ui.screen.trending

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.PER_PAGE_COUNT
import kotlinx.coroutines.flow.Flow

class TrendingPager(
    private val repo: MediaRepository,
    private val config: PagingConfig = PagingConfig(pageSize = PER_PAGE_COUNT),
) {
    fun pagingDataOnFlow(type: String, time: String): Flow<PagingData<Media>> = Pager(config) {
        TrendingPagingDataSource(
            type = type,
            time = time,
            repo = repo
        )
    }.flow
}