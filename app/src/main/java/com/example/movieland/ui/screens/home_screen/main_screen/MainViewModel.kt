package com.example.movieland.ui.screens.home_screen.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieland.core.ApiTools
import com.example.movieland.core.DataStatus
import com.example.movieland.domain.usecase.main.MainUseCase
import com.example.movieland.ui.screens.app_start_screen.intro.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _nowPlayingState = mutableStateOf(MovieState())
    val nowPlayingState: State<MovieState>
        get() = _nowPlayingState

    private val _upcomingState = mutableStateOf(MovieState())
    val upcomingState: State<MovieState>
        get() = _upcomingState

    private val _popularState = mutableStateOf(MovieState())
    val popularState: State<MovieState>
        get() = _popularState

    private val _topRatedState = mutableStateOf(MovieState())
    val topRatedState: State<MovieState>
        get() = _topRatedState

    init {
        loadNowPlayingMovies(page = 1)
        loadUpComingMovies(page = 1)
        loadPopularMovies(page = 1)
        loadTopRatedMovies(page = 1)
    }

    private fun loadNowPlayingMovies(
        language: String = "en_US",
        page: Int = 1,
        token: String = ApiTools.BEARER_TOKEN
    ) {
        mainUseCase.nowPlayingUseCase(language, page, token).onEach {
            when (it) {
                is DataStatus.Loading -> _nowPlayingState.value =
                    _nowPlayingState.value.copy(isLoading = true)

                is DataStatus.Success -> _nowPlayingState.value =
                    _nowPlayingState.value.copy(movies = it.data.movies ?: emptyList())

                is DataStatus.Error -> _nowPlayingState.value =
                    _nowPlayingState.value.copy(error = it.errorMessage)
            }
        }.launchIn(viewModelScope)
    }

    private fun loadUpComingMovies(
        lang: String = "en_US",
        page: Int = 1,
        token: String = ApiTools.BEARER_TOKEN
    ) {
        mainUseCase.upComingUseCase(lang, page, token).onEach {
            when (it) {
                is DataStatus.Loading -> _upcomingState.value =
                    _upcomingState.value.copy(isLoading = true)

                is DataStatus.Success -> _upcomingState.value =
                    _upcomingState.value.copy(movies = it.data.movies ?: emptyList())

                is DataStatus.Error -> _upcomingState.value =
                    _upcomingState.value.copy(error = it.errorMessage)
            }
        }.launchIn(viewModelScope)
    }

    private fun loadPopularMovies(
        lang: String = "en_US",
        page: Int = 1,
        token: String = ApiTools.BEARER_TOKEN
    ) {
        mainUseCase.popularUseCase(lang, page, token).onEach {
            when (it) {
                is DataStatus.Loading -> _popularState.value =
                    _popularState.value.copy(isLoading = true)

                is DataStatus.Success -> _popularState.value =
                    _popularState.value.copy(movies = it.data.movies ?: emptyList())

                is DataStatus.Error -> _popularState.value =
                    _popularState.value.copy(error = it.errorMessage)
            }
        }.launchIn(viewModelScope)
    }

    private fun loadTopRatedMovies(
        lang: String = "en_US",
        page: Int = 1,
        token: String = ApiTools.BEARER_TOKEN
    ) {
        mainUseCase.topRatedUseCase(lang, page, token).onEach {
            when (it) {
                is DataStatus.Loading -> _topRatedState.value =
                    _topRatedState.value.copy(isLoading = true)

                is DataStatus.Success -> _topRatedState.value =
                    _topRatedState.value.copy(movies = it.data.movies ?: emptyList())

                is DataStatus.Error -> _topRatedState.value =
                    _topRatedState.value.copy(error = it.errorMessage)
            }
        }.launchIn(viewModelScope)
    }
}