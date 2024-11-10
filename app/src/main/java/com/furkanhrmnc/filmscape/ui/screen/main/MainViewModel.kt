package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainViewModel(
    private val repo: MediaRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    var splashScreen = mutableStateOf(true)
        private set

    init {
        loadData()
        viewModelScope.launch {
            delay(2.seconds)
            splashScreen.value = false
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            loadTrendingMedias()
            loadOnTheAirCarousel()
            loadMovie()
            loadTvSeries()
        }
    }


    private suspend fun loadTrendingMedias(
        type: String = "movie",
        timeWindow: String = "day",
    ) {
        _uiState.update { it.copy(isLoading = true) }

        repo.getTrendingMovieOrTv(
            type = type,
            timeWindow = timeWindow,
            page = 1
        )
            .collect { result ->
                when (result) {
                    is Response.Failure -> _uiState.update {
                        it.copy(error = result.throwable, isLoading = false)
                    }

                    is Response.Success -> _uiState.update {
                        it.copy(trendingMedias = result.data.results, isLoading = false)
                    }
                }
            }
    }

    private suspend fun loadOnTheAirCarousel() {
        _uiState.update { it.copy(isLoading = true) }

        repo.getMovieOrTv(
            type = "tv",
            category = "on_the_air",
            page = 1
        )
            .collect { result ->
                when (result) {
                    is Response.Failure -> _uiState.update {
                        it.copy(error = result.throwable, isLoading = false)
                    }

                    is Response.Success -> _uiState.update {
                        it.copy(onTheAirCarousel = result.data.results, isLoading = false)
                    }
                }
            }
    }

    private suspend fun loadMovie() {
        _uiState.update { it.copy(isLoading = true) }

        repo.getMovieOrTv(
            type = "movie",
            category = "popular",
            page = 1
        )
            .collect { result ->
                when (result) {
                    is Response.Failure -> _uiState.update {
                        it.copy(error = result.throwable, isLoading = false)
                    }

                    is Response.Success -> _uiState.update {
                        it.copy(movie = result.data.results.first(), isLoading = false)
                    }
                }

            }
    }

    private suspend fun loadTvSeries() {
        _uiState.update { it.copy(isLoading = true) }

        repo.getMovieOrTv(
            type = "tv",
            category = "popular",
            page = 1
        )
            .collect { result ->
                when (result) {
                    is Response.Failure -> _uiState.update {
                        it.copy(error = result.throwable, isLoading = false)
                    }

                    is Response.Success -> _uiState.update {
                        it.copy(tvSeries = result.data.results.first(), isLoading = false)
                    }
                }

            }
    }

    fun onErrorConsumed() {
        _uiState.update { it.copy(error = null) }
    }
}


data class MainUiState(
    val trendingMedias: List<Media> = emptyList(),
    val onTheAirCarousel: List<Media> = emptyList(),
    val movie: Media? = null,
    val tvSeries: Media? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)