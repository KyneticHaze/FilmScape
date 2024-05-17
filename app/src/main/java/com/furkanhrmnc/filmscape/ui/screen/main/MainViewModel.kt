package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieUseCase
import com.furkanhrmnc.filmscape.domain.usecase.CategoryMovieParams
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.toViewPaginated
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    loadMovieUseCase: LoadMovieUseCase,
) : ViewModel() {

    private val error = MutableStateFlow<Throwable?>(null)

    /**
     * [combine] fonksiyonu [LoadMovieUseCase] useCase fonksiyonunun getireceği her bir kategori verisini ayrı ayrı yazmak yerine tek bir yerde yazıp birleştirmek için kullanılır.
     *
     * [combine] içine yazılan veri çekme fonksiyonlarının her biri, [MainUiState] liste film değişkenlerine sayfalanmış biçimlerine dönüşerek yazıldı.
     *
     * [stateIn] fonksiyonu ile scope'un viewModel'da olduğu, paylaşımın abone olduktan 5 saniye sonra bittiği ve başlangıç değerinin [MainUiState]'in constructor'ı olduğunu gösteriyor.
     * */
    val mainUiState: StateFlow<MainUiState> = combine(
        loadMovieUseCase(param = CategoryMovieParams(category = Category.POPULAR)),
        loadMovieUseCase(param = CategoryMovieParams(category = Category.TOP_RATED)),
        loadMovieUseCase(param = CategoryMovieParams(category = Category.UPCOMING)),
        loadMovieUseCase(param = CategoryMovieParams(category = Category.NOW_PLAYING)),
        error
    ) { upcoming, nowPlaying, popular, topRated, error ->
        MainUiState(
            popularViewState = popular.toViewPaginated(),
            nowPlayingViewState = nowPlaying.toViewPaginated(),
            topRatedViewState = topRated.toViewPaginated(),
            upComingViewState = upcoming.toViewPaginated(),
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainUiState()
    )

    fun onError(error: Throwable) {
        viewModelScope.launch {
            this@MainViewModel.error.emit(error)
        }
    }

    /**
     * [MainUiState] değerinin tüketildiği vakit çalışması gereken fonksiyondur.
     */
    fun errorFinish() {
        viewModelScope.launch {
            error.emit(null)
        }
    }
}