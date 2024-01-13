package com.example.movieland.domain.usecase.main

import com.example.movieland.domain.usecase.detail.DetailUseCase
import com.example.movieland.domain.usecase.detail.MovieImageUseCase
import com.example.movieland.domain.usecase.detail.MovieSimilarUseCase

data class MainUseCase(
    val nowPlayingUseCase: NowPlayingUseCase,
    val upComingUseCase: UpComingUseCase,
    val popularUseCase: PopularUseCase,
    val topRatedUseCase: TopRatedUseCase,
    val searchMovieUseCase: SearchMovieUseCase,
    val genresUseCase: GenresUseCase
)