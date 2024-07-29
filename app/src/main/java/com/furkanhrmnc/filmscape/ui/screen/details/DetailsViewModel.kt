package com.furkanhrmnc.filmscape.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieDetailUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadRecommendationMoviesUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadVideosUseCase
import com.furkanhrmnc.filmscape.domain.usecase.RecommendationMovieParams
import com.furkanhrmnc.filmscape.util.toViewPaginated
import com.furkanhrmnc.filmscape.util.toViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsViewModel(
    movieId: Int,
    loadMovieDetailsUseCase: LoadMovieDetailUseCase,
    loadRecommendationMoviesUseCase: LoadRecommendationMoviesUseCase,
    loadVideosUseCase: LoadVideosUseCase
) : ViewModel() {

    private val error = MutableStateFlow<Throwable?>(null)

    /**
     * Bu sabitte detay sayfasının kullanıcı arayüz durumlarını tutuyoruz.
     *
     * [LoadMovieDetailUseCase] - Film Detaylarını
     *
     * [LoadRecommendationMoviesUseCase] - Film ile ilgili önerilen filmleri
     *
     * [LoadVideosUseCase] - Film ile alakalı fragman veya sahneleri sunar.
     *
     * Bu kullanım durumlarını tek kullanıcı arayüzü durumunda tutabilmek için [combine] flow fonksiyonunu kullanarak [DetailsUiState] sınıfının içine aktarıyoruz.
     *
     * Aktarırken [toViewState] fonksiyonu ile use case'leri ViewState tipi içine sokarak durum kontrolünü kolaylaştırıyoruz.
     *
     * @author Furkan Harmancı
     */
    val detailsUiState: StateFlow<DetailsUiState> = combine(
        loadMovieDetailsUseCase(param = movieId),
        loadRecommendationMoviesUseCase(param = RecommendationMovieParams(id = movieId)),
        loadVideosUseCase(param = movieId),
        error
    ) { details,
        recommendedMovies,
        loadVideos,
        error ->
        DetailsUiState(
            movieDetails = details.toViewState(),
            recommendedMovies = recommendedMovies.toViewPaginated(),
            movieVideos = loadVideos.toViewState(),
            error = error
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailsUiState())


    fun onError(error: Throwable) {
        viewModelScope.launch {
            this@DetailsViewModel.error.emit(error)
        }
    }
}