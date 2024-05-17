package com.furkanhrmnc.filmscape.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieDetailUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadRecommendationMoviesUseCase
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
) : ViewModel() {

    private val error = MutableStateFlow<Throwable?>(null)

    val detailsUiState: StateFlow<DetailsUiState> = combine(
        loadMovieDetailsUseCase(param = movieId),
        loadRecommendationMoviesUseCase(param = RecommendationMovieParams(id = movieId)),
        error
    ) { details, recommendedMovies, error ->
        DetailsUiState(
            movieDetail = details.toViewState(),
            recommendedMovies = recommendedMovies.toViewPaginated(),
            error = error
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailsUiState())


    fun onError(error: Throwable) {
        viewModelScope.launch {
            this@DetailsViewModel.error.emit(error)
        }
    }
}