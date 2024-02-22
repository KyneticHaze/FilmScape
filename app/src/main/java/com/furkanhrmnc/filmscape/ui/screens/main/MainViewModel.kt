package com.furkanhrmnc.filmscape.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.core.ApiTools.API_KEY
import com.furkanhrmnc.filmscape.core.Constants
import com.furkanhrmnc.filmscape.core.Constants.ALL
import com.furkanhrmnc.filmscape.core.Constants.DAY
import com.furkanhrmnc.filmscape.core.Constants.MOVIE
import com.furkanhrmnc.filmscape.core.Constants.NOW_PLAYING
import com.furkanhrmnc.filmscape.core.Constants.POPULAR
import com.furkanhrmnc.filmscape.core.Constants.TOP_RATED
import com.furkanhrmnc.filmscape.core.Constants.TV
import com.furkanhrmnc.filmscape.core.Resource
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUIState())
    val mainUiState: StateFlow<MainUIState>
        get() = _mainUiState.asStateFlow()

    init {
        load()
    }

    private fun load(fetchFromRemote: Boolean = false) {
        loadPopularMovies(fetchFromRemote)
        loadTopRatedMovies(fetchFromRemote)
        loadNowPlayingMovies(fetchFromRemote)

        loadPopularTvSeries(fetchFromRemote)
        loadTopRatedTvSeries(fetchFromRemote)

        loadTrendingAll(fetchFromRemote)
    }

    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.Refresh -> {
                _mainUiState.update {
                    it.copy(isLoading = true)
                }
                when (event.type) {
                    Constants.HOME_SCREEN -> {
                        loadTrendingAll(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        loadTopRatedMovies(
                            isRefresh = true
                        )
                        loadNowPlayingMovies(
                            isRefresh = true
                        )
                        loadTopRatedTvSeries(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Constants.TRENDING_SCREEN -> {
                        loadTrendingAll(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Constants.POPULAR_SCREEN -> {
                        loadPopularMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        loadPopularTvSeries(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Constants.TOP_RATED_SCREEN -> {
                        loadTopRatedMovies(
                            isRefresh = true
                        )
                        loadTopRatedTvSeries(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Constants.MOVIE_SCREEN -> {
                        loadPopularMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        loadTopRatedMovies(
                            isRefresh = true
                        )
                        loadNowPlayingMovies(
                            isRefresh = true
                        )
                    }

                    Constants.TV_SCREEN -> {
                        loadPopularTvSeries(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        loadTopRatedTvSeries(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }
                }
            }

            is MainUiEvent.Paginate -> {
                when (event.type) {

                    Constants.TRENDING_SCREEN -> {
                        loadTrendingAll(true)
                    }

                    Constants.POPULAR_SCREEN -> {
                        loadPopularMovies(true)
                    }

                    Constants.TOP_RATED_SCREEN -> {
                        loadTopRatedMovies(true)
                        loadTopRatedTvSeries(true)
                    }

                    Constants.MOVIE_SCREEN -> {
                        loadPopularMovies(true)
                        loadTopRatedMovies(true)
                        loadNowPlayingMovies(true)
                    }

                    Constants.TV_SCREEN -> {
                        loadPopularTvSeries(true)
                        loadTopRatedTvSeries(true)
                    }
                }
            }
        }
    }

    private fun loadPopularMovies(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            mediaRepository.getMedias(
                MOVIE,
                POPULAR,
                mainUiState.value.popularMoviesPage,
                API_KEY
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let { medias ->
                            // verileri değişen bir listeye koy
                            val shuffledMedias = medias.toMutableList()
                            // shuffle -> listedeki ögeleri rastgele karıştırma methodu
                            shuffledMedias.shuffle()

                            if (isRefresh) {
                                // yenilendiğinde
                                _mainUiState.update {
                                    it.copy(
                                        // karıştırılmış değişken listeyi liste şeklinde gir
                                        popularMediaListModel = shuffledMedias.toList(),
                                        popularMoviesPage = 1
                                    )
                                }
                            } else {
                                _mainUiState.update {
                                    it.copy(
                                        // Yenilenmez ise öncekine eklenir
                                        popularMediaListModel = shuffledMedias.toList() + mainUiState.value.popularMediaListModel,
                                        popularMoviesPage = mainUiState.value.popularMoviesPage + 1
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> _mainUiState.update {
                        it.copy(
                            error = resource.errorMessage
                        )
                    }

                    is Resource.Loading -> {
                        _mainUiState.update {
                            it.copy(
                                isLoading = resource.isLoading
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadTopRatedMovies(
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            mediaRepository.getMedias(
                MOVIE,
                TOP_RATED,
                mainUiState.value.topRatedMoviesPage,
                API_KEY
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let { medias ->
                            val shuffledMedias = medias.toMutableList()
                            shuffledMedias.shuffle()

                            if (isRefresh) {
                                _mainUiState.update {
                                    it.copy(
                                        topRatedMediaListModel = shuffledMedias.toList(),
                                        topRatedMoviesPage = 1
                                    )
                                }
                            } else {
                                _mainUiState.update {
                                    it.copy(
                                        topRatedMediaListModel = shuffledMedias.toList() + mainUiState.value.topRatedMediaListModel,
                                        topRatedMoviesPage = mainUiState.value.topRatedTvSeriesPage + 1
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> _mainUiState.update {
                        it.copy(
                            error = resource.errorMessage
                        )
                    }

                    is Resource.Loading -> _mainUiState.update {
                        it.copy(
                            isLoading = resource.isLoading
                        )
                    }
                }
            }
        }
    }

    private fun loadNowPlayingMovies(
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            mediaRepository.getMedias(
                MOVIE,
                NOW_PLAYING,
                _mainUiState.value.nowPlayingMoviesPage,
                API_KEY
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let { medias ->
                            val shuffledMedias = medias.toMutableList()
                            shuffledMedias.shuffle()

                            if (isRefresh) {
                                _mainUiState.update {
                                    it.copy(
                                        nowPlayingMediaListModel = shuffledMedias.toList(),
                                        nowPlayingMoviesPage = 1
                                    )
                                }
                            } else {
                                _mainUiState.update {
                                    it.copy(
                                        nowPlayingMediaListModel = shuffledMedias.toList() + _mainUiState.value.nowPlayingMediaListModel,
                                        nowPlayingMoviesPage = _mainUiState.value.nowPlayingMoviesPage + 1
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        _mainUiState.update {
                            it.copy(
                                error = resource.errorMessage
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _mainUiState.update {
                            it.copy(isLoading = resource.isLoading)
                        }
                    }
                }
            }
        }
    }

    private fun loadPopularTvSeries(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            mediaRepository.getMedias(
                TV,
                POPULAR,
                mainUiState.value.popularTvSeriesPage,
                API_KEY
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let { medias ->
                            val shuffledMedias = medias.toMutableList()
                            shuffledMedias.shuffle()

                            if (isRefresh) {
                                _mainUiState.update {
                                    it.copy(
                                        popularTvSeriesList = shuffledMedias.toList(),
                                        popularTvSeriesPage = 1
                                    )
                                }
                            } else {
                                _mainUiState.update {
                                    it.copy(
                                        popularTvSeriesList = shuffledMedias.toList() + mainUiState.value.popularTvSeriesList,
                                        popularTvSeriesPage = mainUiState.value.popularTvSeriesPage + 1
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        _mainUiState.update {
                            it.copy(
                                error = resource.errorMessage
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _mainUiState.update {
                            it.copy(
                                isLoading = resource.isLoading
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadTopRatedTvSeries(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            mediaRepository.getMedias(
                TV,
                TOP_RATED,
                mainUiState.value.topRatedTvSeriesPage,
                API_KEY
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let { medias ->
                            val shuffledMedias = medias.toMutableList()
                            shuffledMedias.shuffle()

                            if (isRefresh) {
                                _mainUiState.update {
                                    it.copy(
                                        topRatedTvSeriesList = shuffledMedias.toList(),
                                        topRatedTvSeriesPage = 1
                                    )
                                }
                            } else {
                                _mainUiState.update {
                                    it.copy(
                                        topRatedTvSeriesList = shuffledMedias.toList() + mainUiState.value.topRatedTvSeriesList,
                                        topRatedTvSeriesPage = mainUiState.value.topRatedTvSeriesPage + 1
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        _mainUiState.update {
                            it.copy(
                                error = resource.errorMessage
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _mainUiState.update {
                            it.copy(
                                isLoading = resource.isLoading
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadTrendingAll(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            mediaRepository.getTrendingMedias(
                ALL,
                DAY,
                mainUiState.value.trendingAllMediaPage,
                API_KEY
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let { medias ->
                            val shuffledMedias = medias.toMutableList()
                            shuffledMedias.shuffle()

                            if (isRefresh) {
                                _mainUiState.update {
                                    it.copy(
                                        trendingAllMediaListModel = shuffledMedias.toList(),
                                        trendingAllMediaPage = 1
                                    )
                                }
                            } else {
                                _mainUiState.update {
                                    it.copy(
                                        trendingAllMediaListModel = shuffledMedias.toList() + mainUiState.value.trendingAllMediaListModel,
                                        trendingAllMediaPage = mainUiState.value.trendingAllMediaPage + 1
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        _mainUiState.update {
                            it.copy(error = resource.errorMessage)
                        }
                    }

                    is Resource.Loading -> {
                        _mainUiState.update {
                            it.copy(isLoading = resource.isLoading)
                        }
                    }
                }
            }
        }
    }
}