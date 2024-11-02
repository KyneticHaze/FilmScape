package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.PER_PAGE_COUNT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchMoviesPager(
    private val repo: MediaRepository,
    private val config: PagingConfig = PagingConfig(pageSize = PER_PAGE_COUNT),
) {

    private val _query = MutableStateFlow("")

    fun searchPagingDataFlow(type: String): Flow<PagingData<Media>> = _query
        .debounce(timeoutMillis = 400L)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            Pager(config = config) {
                SearchMoviesDataSource(
                    type = type,
                    query = query,
                    repo = repo
                )
            }.flow
        }

    fun onQuery(query: String) {
        _query.tryEmit(query)
    }
}