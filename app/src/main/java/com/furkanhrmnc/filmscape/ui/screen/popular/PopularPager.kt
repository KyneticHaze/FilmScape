package com.furkanhrmnc.filmscape.ui.screen.popular

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.PER_PAGE_COUNT
import kotlinx.coroutines.flow.Flow

class PopularPager(
    private val repo: MediaRepository,
    private val config: PagingConfig = PagingConfig(pageSize = PER_PAGE_COUNT),
) {
    fun pagingDataOnFlow(type: String): Flow<PagingData<Media>> = Pager(config) {
        PopularDataSource(
            type = type,
            repo = repo
        )
    }.flow
}