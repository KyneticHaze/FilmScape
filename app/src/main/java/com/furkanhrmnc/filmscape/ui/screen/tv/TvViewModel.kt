package com.furkanhrmnc.filmscape.ui.screen.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.ui.screen.main.medias.MediaPager
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update

class TvViewModel(private val pager: MediaPager) : ViewModel() {

    private val _uiState = MutableStateFlow(TvUiState(isLoading = true))
    val uiState: StateFlow<TvUiState> = _uiState.asStateFlow()

    init {
        getTvSeries()
    }

    private fun getTvSeries() {
        _uiState.update { it.copy(isLoading = true) }

        _uiState.update {
            it.copy(
                topRatedTvSeries = pager.getMediaDataWithPaging(
                    type = MediaType.TV.lowerName,
                    category = Category.TOP_RATED
                ).catch { t -> onError(t) }
                    .cachedIn(viewModelScope),

                popularTvSeries = pager.getMediaDataWithPaging(
                    type = MediaType.TV.lowerName,
                    category = Category.POPULAR
                ).catch { t -> onError(t) }
                    .cachedIn(viewModelScope),

                isLoading = false
            )
        }
    }

    private fun onError(throwable: Throwable) {
        _uiState.update { it.copy(error = throwable, isLoading = false) }
    }

    fun onErrorConsumed() {
        _uiState.update { it.copy(error = null) }
    }
}

data class TvUiState(
    val topRatedTvSeries: Flow<PagingData<Media>> = emptyFlow(),
    val popularTvSeries: Flow<PagingData<Media>> = emptyFlow(),
    val error: Throwable? = null,
    val isLoading: Boolean = false,
)