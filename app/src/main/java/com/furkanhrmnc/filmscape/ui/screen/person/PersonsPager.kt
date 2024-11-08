package com.furkanhrmnc.filmscape.ui.screen.person

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.PER_PAGE_COUNT

class PersonsPager(
    repo: MediaRepository,
    config: PagingConfig = PagingConfig(pageSize = PER_PAGE_COUNT),
) {
    val personsPagingFlow = Pager(config = config) {
        PersonsPagingDataSource(repo = repo)
    }.flow
}