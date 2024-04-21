package com.furkanhrmnc.filmscape.ui.screen.main

import com.furkanhrmnc.filmscape.domain.model.Movie

data class MainUIState(

    val popularMoviesPage: Int = 1,
    val topRatedMoviesPage: Int = 1,
    val nowPlayingMoviesPage: Int = 1,
    val upcomingMoviesPage: Int = 1,

    val popularTvSeriesPage: Int = 1,
    val topRatedTvSeriesPage: Int = 1,

    val trendingAllPage: Int = 1,

    val error: String = "",
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,

    val popularMoviesList: List<Movie> = emptyList(),
    val topRatedMoviesList: List<Movie> = emptyList(),
    val nowPlayingMoviesList: List<Movie> = emptyList(),
    val upcomingMoviesList: List<Movie> = emptyList(),

    val popularTvSeriesList: List<Movie> = emptyList(),
    val topRatedTvSeriesList: List<Movie> = emptyList(),

    val trendingAllList: List<Movie> = emptyList(),

    // all movie categories list
    val moviesList: List<Movie> = emptyList(),

    // popularTvSeriesList + topRatedTvSeriesList
    val tvSeriesList: List<Movie> = emptyList(),

    // nowPlayingMoviesList + nowPlayingTvSeriesList
    val recommendedAllList: List<Movie> = emptyList(),

    // recommendedAllList + trendingAllList
    val specialList: List<Movie> = emptyList(),
)
