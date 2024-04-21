package com.furkanhrmnc.filmscape.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.util.Screens
import com.furkanhrmnc.filmscape.util.Screens.ALL
import com.furkanhrmnc.filmscape.util.Screens.DAY
import com.furkanhrmnc.filmscape.util.Screens.MOVIE
import com.furkanhrmnc.filmscape.util.Screens.NOW_PLAYING
import com.furkanhrmnc.filmscape.util.Screens.POPULAR
import com.furkanhrmnc.filmscape.util.Screens.TOP_RATED
import com.furkanhrmnc.filmscape.util.Screens.TV
import com.furkanhrmnc.filmscape.util.Screens.UPCOMING
import com.furkanhrmnc.filmscape.util.Resource
import com.furkanhrmnc.filmscape.data.remote.api.MovieApi.Companion.API_KEY
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUIState())
    val mainUiState: StateFlow<MainUIState>
        get() = _mainUiState.asStateFlow()

    init {
        loadAll()
    }

    private fun loadAll(
        fetchFromRemote: Boolean = false
    ) {
        loadPopularMovies(fetchFromRemote)
        loadUpcomingMovies(fetchFromRemote)
        loadNowPlayingMovies(fetchFromRemote)
        loadTopRatedMovies(fetchFromRemote)

        loadPopularTvSeries(fetchFromRemote)
        loadTopRatedTvSeries(fetchFromRemote)

        loadTrendingAll(fetchFromRemote)
    }

    fun onEvent(event: MainUIEvents) {
        when (event) {
            is MainUIEvents.Refresh -> {
                when (event.type) {

                    Screens.MAIN_SCREEN -> {
                        loadTrendingAll(
                            fetchFromRemote = true,
                            isRefresh = true
                        )

                        loadUpcomingMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )

                        loadPopularTvSeries(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Screens.TRENDING_SCREEN -> {
                        loadTrendingAll(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Screens.MOVIES_SCREEN -> {
                        loadPopularMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        loadNowPlayingMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        loadUpcomingMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        loadTopRatedMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Screens.TV_SCREEN -> {
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

            is MainUIEvents.Paginate -> {
                when (event.type) {
                    Screens.TRENDING_SCREEN -> {
                        loadTrendingAll(true)
                    }

                    Screens.MOVIES_SCREEN -> {
                        loadPopularMovies(true)
                        loadUpcomingMovies(true)
                        loadTopRatedMovies(true)
                        loadNowPlayingMovies(true)
                    }

                    Screens.TV_SCREEN -> {
                        loadPopularTvSeries(true)
                        loadTopRatedTvSeries(true)
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
            movieRepository.getTrending(
                fetchFromRemote,
                isRefresh,
                type = ALL,
                time = DAY,
                page = mainUiState.value.trendingAllPage,
                apiKey = API_KEY
            )
                .collect { resource ->
                    when (resource) {
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

                        is Resource.Success -> {
                            resource.data.let { mediaList ->
                                val randomMediaList = mediaList.toMutableList()
                                randomMediaList.shuffle()

                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            trendingAllList = randomMediaList.toList(),
                                            trendingAllPage = 1
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            trendingAllList = mainUiState.value.trendingAllList + randomMediaList.toList(),
                                            trendingAllPage = mainUiState.value.trendingAllPage + 1
                                        )
                                    }
                                }
                                createRecommendedAllList(
                                    movieList = mediaList,
                                    isRefresh = isRefresh
                                )
                            }
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
            movieRepository.getMovies(
                fetchFromRemote,
                isRefresh,
                type = MOVIE,
                category = POPULAR,
                page = mainUiState.value.popularMoviesPage,
                apiKey = API_KEY
            )
                .collect { resource ->
                    when (resource) {
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

                        is Resource.Success -> {
                            resource.data.let { mediaList ->
                                val randomMediaList = mediaList.toMutableList()
                                randomMediaList.shuffle()

                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            popularMoviesList = randomMediaList.toList(),
                                            popularMoviesPage = 1
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            popularMoviesList = mainUiState.value.popularMoviesList + randomMediaList.toList(),
                                            popularMoviesPage = mainUiState.value.popularMoviesPage + 1
                                        )
                                    }
                                }

                                createMoviesList(
                                    movieList = mediaList,
                                    isRefresh = isRefresh
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun loadUpcomingMovies(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            movieRepository.getMovies(
                fetchFromRemote,
                isRefresh,
                type = MOVIE,
                category = UPCOMING,
                page = mainUiState.value.upcomingMoviesPage,
                apiKey = API_KEY
            )
                .collect { resource ->
                    when (resource) {
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

                        is Resource.Success -> {
                            resource.data.let { mediaList ->

                                val randomUpcomingList = mediaList.toMutableList()

                                randomUpcomingList.shuffle()

                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            upcomingMoviesList = randomUpcomingList.toList(),
                                            upcomingMoviesPage = 1
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            upcomingMoviesList = mainUiState.value.upcomingMoviesList + randomUpcomingList.toList(),
                                            upcomingMoviesPage = mainUiState.value.upcomingMoviesPage + 1
                                        )
                                    }
                                }

                                createMoviesList(
                                    movieList = mediaList,
                                    isRefresh = isRefresh
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun loadTopRatedMovies(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            movieRepository.getMovies(
                fetchFromRemote,
                isRefresh,
                type = MOVIE,
                category = TOP_RATED,
                page = mainUiState.value.topRatedMoviesPage,
                apiKey = API_KEY
            )
                .collect { resource ->
                    when (resource) {
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

                        is Resource.Success -> {
                            resource.data.let { mediaList ->

                                val randomTopRatedList = mediaList.toMutableList()

                                randomTopRatedList.shuffle()

                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            topRatedMoviesList = randomTopRatedList.toList(),
                                            topRatedMoviesPage = 1
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            topRatedMoviesList = mainUiState.value.topRatedMoviesList + randomTopRatedList.toList(),
                                            topRatedMoviesPage = mainUiState.value.topRatedMoviesPage + 1
                                        )
                                    }
                                }

                                createMoviesList(
                                    movieList = mediaList,
                                    isRefresh = isRefresh
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun loadNowPlayingMovies(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            movieRepository.getMovies(
                fetchFromRemote,
                isRefresh,
                type = MOVIE,
                category = NOW_PLAYING,
                page = mainUiState.value.nowPlayingMoviesPage,
                apiKey = API_KEY
            )
                .collect { resource ->
                    when (resource) {
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

                        is Resource.Success -> {
                            resource.data.let { mediaList ->
                                val randomNowPlayingList = mediaList.toMutableList()

                                randomNowPlayingList.shuffle()

                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            nowPlayingMoviesList = randomNowPlayingList.toList(),
                                            nowPlayingMoviesPage = 1
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            nowPlayingMoviesList = mainUiState.value.nowPlayingMoviesList + randomNowPlayingList.toList(),
                                            nowPlayingMoviesPage = mainUiState.value.nowPlayingMoviesPage + 1
                                        )
                                    }
                                }

                                createMoviesList(
                                    movieList = mediaList,
                                    isRefresh = isRefresh
                                )
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
            movieRepository.getMovies(
                fetchFromRemote,
                isRefresh,
                type = TV,
                category = POPULAR,
                page = mainUiState.value.popularTvSeriesPage,
                apiKey = API_KEY
            )
                .collect { resource ->
                    when (resource) {
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

                        is Resource.Success -> {

                            resource.data.let { mediaList ->

                                val popularTvSeriesList = mediaList.toMutableList()

                                popularTvSeriesList.shuffle()

                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            popularTvSeriesList = popularTvSeriesList.toList(),
                                            popularTvSeriesPage = 1
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            popularTvSeriesList = mainUiState.value.popularTvSeriesList + popularTvSeriesList.toList(),
                                            popularTvSeriesPage = mainUiState.value.popularTvSeriesPage + 1
                                        )
                                    }
                                }

                                createTvSeriesList(
                                    movieList = mediaList,
                                    isRefresh = isRefresh
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
            movieRepository.getMovies(
                fetchFromRemote,
                isRefresh,
                type = TV,
                category = TOP_RATED,
                page = mainUiState.value.topRatedTvSeriesPage,
                apiKey = API_KEY
            )
                .collect { resource ->
                    when (resource) {
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

                        is Resource.Success -> {
                            resource.data.let { mediaList ->
                                val topRatedTvSeriesListRandom = mediaList.toMutableList()

                                topRatedTvSeriesListRandom.shuffle()

                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            topRatedTvSeriesList = topRatedTvSeriesListRandom.toList(),
                                            topRatedTvSeriesPage = 1
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            topRatedTvSeriesList = mainUiState.value.topRatedTvSeriesList + topRatedTvSeriesListRandom.toList(),
                                            topRatedTvSeriesPage = mainUiState.value.topRatedTvSeriesPage + 1
                                        )
                                    }
                                }

                                createTvSeriesList(
                                    movieList = mediaList,
                                    isRefresh = isRefresh
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun createMoviesList(
        movieList: List<Movie>,
        isRefresh: Boolean
    ) {
        val randomMoviesList = movieList.toMutableList()

        randomMoviesList.shuffle()

        if (isRefresh) {
            _mainUiState.update {
                it.copy(
                    moviesList = randomMoviesList.toList()
                )
            }
        } else {
            _mainUiState.update {
                it.copy(
                    moviesList = mainUiState.value.moviesList + randomMoviesList.toList()
                )
            }
        }
    }

    private fun createTvSeriesList(
        movieList: List<Movie>,
        isRefresh: Boolean
    ) {
        val randomTvSeriesList = movieList.toMutableList()

        randomTvSeriesList.shuffle()

        if (isRefresh) {
            _mainUiState.update {
                it.copy(
                    tvSeriesList = randomTvSeriesList.toList()
                )
            }
        } else {
            _mainUiState.update {
                it.copy(
                    tvSeriesList = mainUiState.value.tvSeriesList + randomTvSeriesList.toList()
                )
            }
        }
    }

    private fun createRecommendedAllList(
        movieList: List<Movie>,
        isRefresh: Boolean
    ) {
        val randomMediaList = movieList.toMutableList()
        randomMediaList.shuffle()

        if (isRefresh) {
            _mainUiState.update {
                it.copy(
                    recommendedAllList = randomMediaList.toList()
                )
            }
        } else {
            _mainUiState.update {
                it.copy(
                    recommendedAllList = mainUiState.value.recommendedAllList + randomMediaList.toList()
                )
            }
        }
        createSpecialList(
            movieList = movieList,
            isRefresh = isRefresh
        )
    }

    private fun createSpecialList(
        movieList: List<Movie>,
        isRefresh: Boolean = false
    ) {
        if (isRefresh) {
            _mainUiState.update {
                it.copy(
                    specialList = emptyList()
                )
            }
        }

        if (mainUiState.value.specialList.size >= 7) {
            return
        }


        val specialListTakeSevenItem = movieList.take(7).toMutableList()
        specialListTakeSevenItem.shuffle()

        if (isRefresh) {
            _mainUiState.update {
                it.copy(
                    specialList = specialListTakeSevenItem.toList()
                )
            }
        } else {
            _mainUiState.update {
                it.copy(
                    specialList = mainUiState.value.specialList + specialListTakeSevenItem.toList()
                )
            }
        }
    }
}