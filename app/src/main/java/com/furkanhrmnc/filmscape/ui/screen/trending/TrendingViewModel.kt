package com.furkanhrmnc.filmscape.ui.screen.trending

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.util.BaseViewModel
import com.furkanhrmnc.filmscape.util.MediaType
import com.furkanhrmnc.filmscape.util.Time
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update

class TrendingViewModel(pager: TrendingPager) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TrendUiState())
    val uiState = _uiState.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val trendMedia: Flow<PagingData<Media>> = _uiState
        .flatMapLatest { uiState ->
            pager.pagingDataOnFlow(
                type = MediaType.MOVIE.lowerName,
                time = uiState.period.name.lowercase(),
            )
                .catch { throwable -> handleError(throwable) }
        }.cachedIn(viewModelScope)

    fun changePeriod(time: Time) {
        _uiState.update {
            it.copy(
                period = time,
                option = time.name
            )
        }
    }

    fun toggle() {
        _uiState.update { it.copy(expanded = !it.expanded) }
    }
}