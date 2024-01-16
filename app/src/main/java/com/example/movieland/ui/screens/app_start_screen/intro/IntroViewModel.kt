package com.example.movieland.ui.screens.app_start_screen.intro

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieland.core.ApiTools
import com.example.movieland.core.DataStatus
import com.example.movieland.data.remote.dto.commonDto.MovieDTO
import com.example.movieland.domain.usecase.main.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _movieState = mutableStateOf(MovieState())
    val movieState: State<MovieState>
        get() = _movieState

    init {
        loadUpComingMovies(page = 4)
    }

    private fun loadUpComingMovies(
        lang: String = "en_US",
        page: Int,
        token: String = ApiTools.BEARER_TOKEN
    ) {
        mainUseCase.upComingUseCase(lang, page, token).onEach { status ->
            when (status) {
                is DataStatus.Loading -> _movieState.value = _movieState.value.copy(isLoading = true)
                is DataStatus.Success -> _movieState.value = _movieState.value.copy(movies = status.data.movies ?: emptyList())
                is DataStatus.Error -> _movieState.value = _movieState.value.copy(error = status.errorMessage)
            }
        }.launchIn(viewModelScope)
    }
}

data class MovieState(
    val isLoading: Boolean = false,
    val movies: List<MovieDTO> = emptyList(),
    val error: String = ""
)