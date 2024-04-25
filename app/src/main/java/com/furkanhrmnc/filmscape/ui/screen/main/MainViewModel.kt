package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieUseCase
import com.furkanhrmnc.filmscape.domain.usecase.Param
import com.furkanhrmnc.filmscape.domain.usecase.toViewPaginated
import com.furkanhrmnc.filmscape.util.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    loadMovieUseCase: LoadMovieUseCase
) : ViewModel() {

    private val error = MutableStateFlow<Throwable?>(null)

    val mainUiState: StateFlow<MainUIState> = combine(
        loadMovieUseCase(param = Param(category = Category.POPULAR)),
        loadMovieUseCase(param = Param(category = Category.TOP_RATED)),
        loadMovieUseCase(param = Param(category = Category.UP_COMING)),
        loadMovieUseCase(param = Param(category = Category.NOW_PLAYING)),
        error
    ) { upcoming, nowPlaying, popular, topRated, error ->
        MainUIState(
            popularViewState = popular.toViewPaginated(),
            nowPlayingViewState = nowPlaying.toViewPaginated(),
            topRatedViewState = topRated.toViewPaginated(),
            upComingViewState = upcoming.toViewPaginated(),
            error = error
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), initialValue = MainUIState())

    fun onError(error: Throwable) {
        viewModelScope.launch {
            this@MainViewModel.error.emit(error)
        }
    }

    fun onErrorConsumed() {
        viewModelScope.launch {
            error.emit(null)
        }
    }
}