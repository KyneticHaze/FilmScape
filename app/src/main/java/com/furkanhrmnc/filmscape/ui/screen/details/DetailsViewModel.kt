package com.furkanhrmnc.filmscape.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieDetailsUseCase
import com.furkanhrmnc.filmscape.domain.usecase.toViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    movieId: Int,
    loadMovieDetailsUseCase: LoadMovieDetailsUseCase
): ViewModel() {

    private val error = MutableStateFlow<Throwable?>(null)

    val detailsUiState: StateFlow<DetailsUiState> = combine(
        loadMovieDetailsUseCase(input = movieId),
        error
    ) { details, error ->
        DetailsUiState(
            detail = details.toViewState(),
            error = error
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailsUiState())
}