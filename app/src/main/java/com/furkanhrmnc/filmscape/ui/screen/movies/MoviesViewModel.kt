package com.furkanhrmnc.filmscape.ui.screen.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.ui.screen.main.MediaPager
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update

class MoviesViewModel(private val pager: MediaPager) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState(isLoading = true))
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        _uiState.update { it.copy(isLoading = true) }

        _uiState.update {
            it.copy(
                popularMovies = pager.getMediaDataWithPaging(
                    type = MediaType.MOVIE.lowerName,
                    category = Category.POPULAR
                ).catch { t -> onError(t) }
                    .cachedIn(viewModelScope),

                topRatedMovies = pager.getMediaDataWithPaging(
                    type = MediaType.MOVIE.lowerName,
                    category = Category.TOP_RATED
                ).catch { t -> onError(t) }
                    .cachedIn(viewModelScope),

                upcomingMovies = pager.getMediaDataWithPaging(
                    type = MediaType.MOVIE.lowerName,
                    category = Category.UPCOMING
                ).catch { t -> onError(t) }
                    .cachedIn(viewModelScope),

                nowPlayingMovies = pager.getMediaDataWithPaging(
                    type = MediaType.MOVIE.lowerName,
                    category = Category.NOW_PLAYING
                ).catch { t -> onError(t) }
                    .cachedIn(viewModelScope),

                isLoading = false
            )
        }
    }

    private fun onError(t: Throwable) {
        _uiState.update { it.copy(error = t, isLoading = false) }
    }
}