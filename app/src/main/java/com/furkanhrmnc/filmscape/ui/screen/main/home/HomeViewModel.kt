package com.furkanhrmnc.filmscape.ui.screen.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.getDataOrNull
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: MediaRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val movie = async { loadMovie() }
                val tvSeries = async { loadTvSeries() }
                val onTheAirCarousel = async { loadOnTheAirCarousel() }
                val trendingMedias = async { loadTrendingMedias() }

                _uiState.update {
                    it.copy(
                        movie = movie.await(),
                        tvSeries = tvSeries.await(),
                        onTheAirCarousel = onTheAirCarousel.await() ?: emptyList(),
                        trendingMedias = trendingMedias.await() ?: emptyList(),
                        isLoading = false
                    )
                }
            } catch (e: Throwable) {
                _uiState.update { it.copy(error = e, isLoading = false) }
            }
        }
    }


    suspend fun loadTrendingMedias() =
        repo.getTrendingMovieOrTv("movie", "day", 1).getDataOrNull()?.results

    suspend fun loadOnTheAirCarousel() =
        repo.getMovieOrTv("tv", "on_the_air", 1).getDataOrNull()?.results

    suspend fun loadMovie() =
        repo.getMovieOrTv("movie", "popular", 1).getDataOrNull()?.results?.firstOrNull()

    suspend fun loadTvSeries() =
        repo.getMovieOrTv("tv", "popular", 1).getDataOrNull()?.results?.firstOrNull()

}