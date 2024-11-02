package com.furkanhrmnc.filmscape.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.YOUTUBE_BASE_URL
import com.furkanhrmnc.filmscape.util.MediaType
import com.furkanhrmnc.filmscape.util.Result
import com.furkanhrmnc.filmscape.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    val id: Int,
    val player: Player,
    private val repo: MediaRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch {
            loadMediaDetail(MediaType.MOVIE.name)
            loadRecommendations(MediaType.MOVIE.name)
            loadVideoByType(MediaType.MOVIE.name)
        }
    }

    fun onEvent(event: DetailUiEvent) {
        when (event) {
            is DetailUiEvent.AddFavorite -> addMovieToFavorites(event.media)
            is DetailUiEvent.PlayVideo -> playVideo(event.videoId)
            is DetailUiEvent.Navigate -> sendUiEvent(UiEvent.NavigateTo(event.route))
        }
    }

    private fun addMovieToFavorites(media: Media) {
        viewModelScope.launch {
            repo.addMediaToCache(media)
        }
    }

    private fun playVideo(videoId: String) {
        val youtubeUrl = "${YOUTUBE_BASE_URL}${videoId}"
        player.setMediaItem(MediaItem.fromUri(youtubeUrl))
        player.prepare()
        player.play()
    }

    private suspend fun loadMediaDetail(type: String) {

        _uiState.update { it.copy(isLoading = true) }

        repo.getDetailMediaOrTv(
            type = type,
            id = id
        )
            .collect { result ->
                _uiState.update {
                    when (result) {
                        is Result.Failure -> it.copy(
                            error = result.throwable,
                            isLoading = false
                        )

                        is Result.Success -> it.copy(
                            mediaDetail = result.data,
                            isLoading = false
                        )
                    }
                }
            }
    }

    private suspend fun loadRecommendations(type: String) {

        _uiState.update { it.copy(isLoading = true) }

        repo.getRecommendationsMovieOrTv(
            type = type,
            id = id,
            page = 1
        )
            .collect { result ->
                _uiState.update {
                    when (result) {
                        is Result.Failure -> it.copy(
                            error = result.throwable,
                            isLoading = false
                        )

                        is Result.Success -> it.copy(
                            recommendedMedias = result.data.results,
                            isLoading = false
                        )
                    }
                }
            }
    }

    private suspend fun loadVideoByType(type: String) {
        repo.getVideosMovieOrTv(type = type, id = id)
            .collect { result ->
                _uiState.update {
                    when (result) {
                        is Result.Failure -> it.copy(error = result.throwable)
                        is Result.Success -> it.copy(videoKey = result.data.key)
                    }
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}