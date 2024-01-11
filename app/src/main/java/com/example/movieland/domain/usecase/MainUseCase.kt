package com.example.movieland.domain.usecase

data class MainUseCase(
    val nowPlayingUseCase: NowPlayingUseCase,
    val upComingUseCase: UpComingUseCase,
    val popularUseCase: PopularUseCase,
    val topRatedUseCase: TopRatedUseCase,
    val detailUseCase: DetailUseCase,
    val searchMovieUseCase: SearchMovieUseCase,
    val movieSimilarUseCase: MovieSimilarUseCase,
    val movieImageUseCase: MovieImageUseCase
)