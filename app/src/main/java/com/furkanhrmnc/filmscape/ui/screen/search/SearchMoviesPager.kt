package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.usecase.LoadSearchMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchMoviesPager(
    loadSearchMoviesUseCase: LoadSearchMoviesUseCase,
    config: PagingConfig = PagingConfig(pageSize = 20),
) {

    private val query = MutableStateFlow("")

    val searchPagingDataFlow: Flow<PagingData<Movie>> = query
        .debounce(timeoutMillis = 400L)
        .distinctUntilChanged()
        .flatMapLatest {
            Pager(config = config) {
                SearchMoviesDataSource(
                    query = it,
                    loadSearchMoviesUseCase = loadSearchMoviesUseCase
                )
            }.flow
        }

    fun onQuery(query: String) {
        this.query.value = query
    }
}