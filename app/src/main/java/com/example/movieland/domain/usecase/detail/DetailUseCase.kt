package com.example.movieland.domain.usecase.detail

data class DetailUseCase(
    val  detailMovieUseCase: DetailMovieUseCase,
    val movieSimilarUseCase: MovieSimilarUseCase,
    val movieImageUseCase: MovieImageUseCase,
    val movieCreditsUseCase: MovieCreditsUseCase
)