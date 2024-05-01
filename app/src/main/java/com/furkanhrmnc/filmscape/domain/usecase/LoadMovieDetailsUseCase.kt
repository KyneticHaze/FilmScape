package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.util.NetworkOperation
import com.furkanhrmnc.filmscape.util.asResult
import kotlinx.coroutines.flow.Flow

class LoadMovieDetailsUseCase(
    private val repository: MovieRepository,
) : UseCase<Int, MovieDetails> {
    override fun invoke(input: Int): Flow<NetworkOperation<MovieDetails>> =
        repository.getMovieDetails(input).asResult()
}