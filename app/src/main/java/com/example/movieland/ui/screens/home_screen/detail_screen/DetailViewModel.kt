package com.example.movieland.ui.screens.home_screen.detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieland.core.ApiTools
import com.example.movieland.core.DataStatus
import com.example.movieland.data.remote.dto.credit.Cast
import com.example.movieland.data.remote.dto.detail.DetailDTO
import com.example.movieland.data.remote.dto.image.Poster
import com.example.movieland.domain.usecase.detail.DetailUseCase
import com.example.movieland.ui.screens.app_start_screen.intro.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: DetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailState = mutableStateOf(DetailState())
    val detailState : State<DetailState>
        get() = _detailState

    private val _similarState = mutableStateOf(MovieState())
    val similarState : State<MovieState>
        get() = _similarState

    private val _imageState = mutableStateOf(ImageState())
    val imageState : State<ImageState>
        get() = _imageState

    private val _creditState = mutableStateOf(CreditState())
    val creditState : State<CreditState>
        get() = _creditState

    init {
        savedStateHandle.get<Int>("movie_id")?.let {
            loadMovieDetail(it)
            loadMovieCredits(it)
            loadSimilarMovies(it, page = 5)
            loadMovieImage(it, page = 3)
        }
    }

    private fun loadMovieDetail(
        movieId : Int,
        token : String = ApiTools.BEARER_TOKEN
    ) {
        detailUseCase.detailMovieUseCase(movieId, token).onEach {
            when (it) {
                is DataStatus.Loading -> _detailState.value = _detailState.value.copy(isLoading = true)
                is DataStatus.Success -> _detailState.value = _detailState.value.copy(movieDetail = it.data)
                is DataStatus.Error -> _detailState.value = _detailState.value.copy(error = it.errorMessage)
            }
        }.launchIn(viewModelScope)
    }

    private fun loadSimilarMovies(
        movieId : Int,
        lang : String = "en_US",
        page : Int,
        token : String = ApiTools.BEARER_TOKEN
    ) {
        detailUseCase.movieSimilarUseCase(movieId, lang, page, token).onEach {
            when (it) {
                is DataStatus.Loading -> _similarState.value = _similarState.value.copy(isLoading = true)
                is DataStatus.Success -> _similarState.value = _similarState.value.copy(movies = it.data)
                is DataStatus.Error -> _similarState.value = _similarState.value.copy(error = it.errorMessage)
            }
        }.launchIn(viewModelScope)
    }

    private fun loadMovieImage(
        movieId: Int,
        page: Int,
        token: String = ApiTools.BEARER_TOKEN
    ) {
        detailUseCase.movieImageUseCase(movieId, page, token).onEach {
            when (it) {
                is DataStatus.Loading -> _imageState.value = _imageState.value.copy(isLoading = true)
                is DataStatus.Success -> _imageState.value = _imageState.value.copy(posters = it.data)
                is DataStatus.Error -> _imageState.value = _imageState.value.copy(error = it.errorMessage)
            }
        }.launchIn(viewModelScope)
    }

    private fun loadMovieCredits(
        movieId: Int,
        token : String = ApiTools.BEARER_TOKEN
    ) {
        detailUseCase.movieCreditsUseCase(movieId, token).onEach {
            when (it) {
                is DataStatus.Loading -> _creditState.value = _creditState.value.copy(isLoading = true)
                is DataStatus.Success -> _creditState.value = _creditState.value.copy(casts = it.data)
                is DataStatus.Error -> _creditState.value = _creditState.value.copy(error = it.errorMessage)
            }
        }.launchIn(viewModelScope)
    }
}

data class DetailState(
    val isLoading : Boolean = false,
    val movieDetail : DetailDTO? = null,
    val error : String = ""
)

data class ImageState(
    val isLoading : Boolean = false,
    val posters : List<Poster> = emptyList(),
    val error : String = ""
)

data class CreditState(
    val isLoading : Boolean = false,
    val casts : List<Cast> = emptyList(),
    val error : String = ""
)