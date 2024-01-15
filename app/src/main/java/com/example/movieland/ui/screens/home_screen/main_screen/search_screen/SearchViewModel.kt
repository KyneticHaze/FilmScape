package com.example.movieland.ui.screens.home_screen.main_screen.search_screen

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
class SearchViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _movieState = mutableStateOf(MovieState())
    val movieState: State<MovieState>
        get() = _movieState

    var searchQuery = ""

    fun searchMovie(
        query: String,
        page : Int,
        token : String = ApiTools.BEARER_TOKEN
    ) {
        searchQuery = query
        mainUseCase.searchMovieUseCase(
            query,
            page,
            token
        ).onEach {
            when (it) {
                is DataStatus.Loading -> _movieState.value = _movieState.value.copy(isLoading = true)
                is DataStatus.Success -> _movieState.value = _movieState.value.copy(movies = it.data)
                is DataStatus.Error -> _movieState.value = _movieState.value.copy(error = it.errorMessage)
            }
        }.launchIn(viewModelScope)
    }

    fun clearSearch() {
        searchQuery = ""
    }
}