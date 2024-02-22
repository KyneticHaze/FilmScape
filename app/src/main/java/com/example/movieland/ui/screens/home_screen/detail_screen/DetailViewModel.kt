package com.example.movieland.ui.screens.home_screen.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieland.core.ApiTools
import com.example.movieland.core.Resource
import com.example.movieland.domain.repository.DetailRepository
import com.example.movieland.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState: StateFlow<DetailUiState>
        get() = _detailUiState.asStateFlow()


    fun onEvent(event: DetailEvents) {
        when (event) {
            is DetailEvents.Refresh -> {
                _detailUiState.update {
                    it.copy(
                        isLoading = true
                    )
                }

                startLoad(true)
            }
            is DetailEvents.SetDataOnLoad -> {
                startLoad(
                    isRefresh = false,
                    id = event.id)
            }
        }
    }

    private fun startLoad(
        isRefresh: Boolean,
        id: Int = detailUiState.value.media?.id ?: 0
    ) {
        loadMediaItem(id) {
            loadMediaDetails(isRefresh = isRefresh)
            loadSimilarMediaList()
        }
    }

    private fun loadMediaItem(
        id: Int,
        onFinish: () -> Unit
    ) {
        viewModelScope.launch {
            _detailUiState.update {
                it.copy(
                    media = mediaRepository.getMedia(id = id)
                )
            }
            onFinish()
        }
    }

    private fun loadMediaDetails(
        isRefresh: Boolean
    ) {
        viewModelScope.launch {
            detailRepository.getMediaDetail(
                isRefresh = isRefresh,
                mediaId = _detailUiState.value.media?.id ?: 0,
                apiKey = ApiTools.API_KEY
            )
                .collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _detailUiState.update {
                                it.copy(
                                    isLoading = resource.isLoading
                                )
                            }
                        }
                        is Resource.Success -> {
                            resource.data.let {
                                _detailUiState.update {
                                    it.copy(
                                        media = detailUiState.value.media
                                    )
                                }
                            }
                        }

                        is Resource.Error -> {
                            _detailUiState.update {
                                it.copy(
                                    errorMessage = resource.errorMessage
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun loadSimilarMediaList() {
        viewModelScope.launch {
            detailRepository.getSimilarMedias(
                mediaId = detailUiState.value.media?.id ?: 1,
                page = 1,
                apiKey = ApiTools.API_KEY
            )
                .collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _detailUiState.update {
                                it.copy(
                                    isLoading = resource.isLoading
                                )
                            }
                        }
                        is Resource.Success -> {
                            resource.data.let { similarMedias ->
                                _detailUiState.update {
                                    it.copy(
                                        similarMediaListModel = similarMedias
                                    )
                                }
                            }
                        }
                        is Resource.Error -> {
                            _detailUiState.update {
                                it.copy(
                                    errorMessage = resource.errorMessage
                                )
                            }
                        }
                    }
                }
        }
    }
}