package com.furkanhrmnc.filmscape.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.FAVORITES
import com.furkanhrmnc.filmscape.util.Constants.MEDIA
import com.furkanhrmnc.filmscape.util.MediaType
import com.furkanhrmnc.filmscape.util.Response
import com.furkanhrmnc.filmscape.util.UiEvent
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(private val repo: MediaRepository) : ViewModel() {

    private val firestore = Firebase.firestore
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: DetailsUiEvents) {
        when (event) {
            is DetailsUiEvents.Navigate -> sendUiEvent(UiEvent.NavigateTo(event.route))
            is DetailsUiEvents.SetDataAndLoad -> {
                startLoad(
                    id = event.id,
                    type = event.type
                )
            }

            is DetailsUiEvents.AddFavorite -> addMediaToFavorites(event.uid, event.media)
        }
    }

    private fun startLoad(
        id: Int,
        type: MediaType,
    ) {
        viewModelScope.launch {
            loadMediaDetail(id, type.lowerName)
            loadRecommendations(id, type.lowerName)
            loadMediaDetailCasts(id, type.lowerName)
            loadMediaDetailVideos(id, type.lowerName)
        }
    }

    private fun addMediaToFavorites(uid: String, media: Media) {
        firestore
            .collection(FAVORITES)
            .document(uid)
            .collection(MEDIA)
            .document(media.id.toString())
            .set(media)
            .addOnSuccessListener {
                _uiState.update { it.copy(isFavorite = true) }
            }
            .addOnFailureListener { e ->
                _uiState.update { it.copy(isFavorite = false, error = e) }
            }
    }

    private suspend fun loadMediaDetail(
        id: Int,
        type: String,
    ) {

        _uiState.update { it.copy(isLoading = true) }

        repo.getDetailMediaOrTv(
            type = type,
            id = id
        )
            .collect { result ->
                _uiState.update {
                    when (result) {
                        is Response.Failure -> it.copy(
                            error = result.throwable,
                            isLoading = false
                        )

                        is Response.Success -> it.copy(
                            mediaDetail = result.data,
                            isLoading = false
                        )
                    }
                }
            }
    }

    private suspend fun loadRecommendations(
        id: Int,
        type: String,
    ) {

        _uiState.update { it.copy(isLoading = true) }

        repo.getRecommendationsMovieOrTv(
            type = type,
            id = id,
            page = 1
        )
            .collect { result ->
                _uiState.update {
                    when (result) {
                        is Response.Failure -> it.copy(
                            error = result.throwable,
                            isLoading = false
                        )

                        is Response.Success -> it.copy(
                            recommendedMovies = result.data.results,
                            isLoading = false
                        )
                    }
                }
            }
    }

    private suspend fun loadMediaDetailCasts(
        id: Int,
        type: String,
    ) {
        _uiState.update { it.copy(isLoading = true) }
        repo.getCreditsMovieOrTv(type = type, id = id).collect { result ->
            when (result) {
                is Response.Failure -> _uiState.update {
                    it.copy(error = result.throwable, isLoading = false)
                }

                is Response.Success -> _uiState.update {
                    it.copy(movieCasts = result.data, isLoading = false)
                }
            }
        }
    }

    private suspend fun loadMediaDetailVideos(
        id: Int,
        type: String,
    ) {
        repo.getVideosMovieOrTv(type = type, id = id)
            .collect { result ->
                _uiState.update {
                    when (result) {
                        is Response.Failure -> it.copy(error = result.throwable)
                        is Response.Success -> it.copy(videoId = result.data.shuffled()[0].key)
                    }
                }
            }
    }

    fun onError(throwable: Throwable) {
        _uiState.update { it.copy(error = throwable) }
    }

    fun onErrorConsumed() {
        _uiState.update { it.copy(error = null) }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}