package com.furkanhrmnc.filmscape.ui.screens.main_screen

import com.furkanhrmnc.filmscape.domain.model.Media

data class MainUIState(

    val popularMoviesPage: Int = 1,
    val topRatedMoviesPage: Int = 1,
    val nowPlayingMoviesPage: Int = 1,

    val popularTvSeriesPage: Int = 1,
    val topRatedTvSeriesPage: Int = 1,

    val trendingAllMediaPage: Int = 1,

    val error: String = "",
    val isRefresh: Boolean = false,
    val isLoading: Boolean = false,

    val popularMediaListModel : List<Media> = emptyList(),
    val topRatedMediaListModel : List<Media> = emptyList(),
    val nowPlayingMediaListModel : List<Media> = emptyList(),

    val popularTvSeriesList : List<Media> = emptyList(),
    val topRatedTvSeriesList : List<Media> = emptyList(),

    val trendingAllMediaListModel : List<Media> = emptyList(),
)
