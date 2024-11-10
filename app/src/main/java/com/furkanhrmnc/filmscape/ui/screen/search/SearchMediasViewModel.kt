package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.util.Constants.SUBSCRIBED_MS
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchMediasViewModel(pager: SearchMediasPager) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search
        .onEach(pager::onQuery)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(SUBSCRIBED_MS),
            _search.value
        )

    val searchMedias = pager.searchPagingDataFlow(MediaType.MOVIE.name.lowercase())
        .catch { throwable -> onError(throwable) }
        .cachedIn(viewModelScope)


    init {
        observeSearchResults()
    }

    private fun observeSearchResults() {
        viewModelScope.launch {
            searchMedias.collect { pagingDataMedia ->
                _uiState.update {
                    it.copy(
                        searchMedias = pagingDataMedia,
                        isLoading = false
                    )
                }
            }
        }
    }


    fun onSearch(query: String) {
        _search.value = query
        _uiState.update {
            it.copy(
                query = query,
                isLoading = true
            )
        }
    }

    fun onClear() {
        _search.value = ""
        _uiState.update {
            it.copy(
                query = "",
                isLoading = false
            )
        }
    }


    fun onError(throwable: Throwable) {
        _uiState.update { it.copy(error = throwable, isLoading = false) }
    }

    fun onErrorConsumed() {
        _uiState.update { it.copy(error = null) }
    }
}


data class SearchUiState(
    val query: String = "",
    val searchMedias: PagingData<Media> = PagingData.empty(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)