package com.furkanhrmnc.filmscape.ui.screen.main.medias

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.Constants.PER_PAGE_COUNT


class MediaPager(
    private val repo: MediaRepository,
    private val config: PagingConfig = PagingConfig(pageSize = PER_PAGE_COUNT),
) {
    fun getMediaDataWithPaging(
        type: String,
        category: Category,
    ) = Pager(config = config) {
        MediaPagingDataSource(
            type = type,
            category = category,
            repo = repo
        )
    }.flow
}