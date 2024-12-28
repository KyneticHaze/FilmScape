package com.furkanhrmnc.filmscape.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.model.Cast
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.MediaDetail
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.FAVORITES
import com.furkanhrmnc.filmscape.util.Constants.MEDIA
import com.furkanhrmnc.filmscape.util.Constants.UNKNOWN_ERROR
import com.furkanhrmnc.filmscape.util.MediaType
import com.furkanhrmnc.filmscape.util.UiEvent
import com.furkanhrmnc.filmscape.util.getDataOrNull
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
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
            is DetailsUiEvents.SetDataAndLoad -> startLoad(event.id, event.type)
            is DetailsUiEvents.AddFavorite -> addMediaToFavorites(event.uid, event.media)
        }
    }

    private fun startLoad(id: Int, type: MediaType) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val mediaDetail = async { loadMediaDetail(type.lowerName, id) }
                val recommendations = async { loadRecommendations(type.lowerName, id) }
                val casts = async { loadMediaDetailCasts(type.lowerName, id) }
                val videoId = async { loadMediaDetailVideos(type.lowerName, id) }

                _uiState.update {
                    it.copy(
                        mediaDetail = mediaDetail.await(),
                        recommendedMovies = recommendations.await() ?: emptyList(),
                        movieCasts = casts.await() ?: emptyList(),
                        videoId = videoId.await() ?: "",
                        isLoading = false
                    )
                }
            } catch (e: Throwable) {
                _uiState.update { it.copy(error = e, isLoading = false) }
            }
        }
    }

    private suspend fun loadMediaDetail(type: String, id: Int): MediaDetail? =
        repo.getDetailMediaOrTv(type, id).getDataOrNull()

    private suspend fun loadRecommendations(type: String, id: Int): List<Media>? =
        repo.getRecommendationsMovieOrTv(type, id, page = 1).getDataOrNull()?.results


    private suspend fun loadMediaDetailCasts(type: String, id: Int): List<Cast>? =
        repo.getCreditsMovieOrTv(type, id).getDataOrNull()

    private suspend fun loadMediaDetailVideos(type: String, id: Int): String? =
        repo.getVideosMovieOrTv(type, id).getDataOrNull()?.firstOrNull()?.key

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

    fun onError(throwable: Throwable) {
        sendUiEvent(UiEvent.ShowSnackbar(throwable.message ?: UNKNOWN_ERROR))
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}