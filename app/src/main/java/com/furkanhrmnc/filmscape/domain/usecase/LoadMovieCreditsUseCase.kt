package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.details.credit.Cast
import com.furkanhrmnc.filmscape.domain.model.details.credit.Crew
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.util.NetworkOperation
import com.furkanhrmnc.filmscape.util.asNetworkOperationFlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoadCrewUseCase(private val repository: MovieRepository) : Caseable<Int, List<Crew>> {
    override fun invoke(param: Int): Flow<NetworkOperation<List<Crew>>> {
        return repository.getMovieCredits(movieId = param).map { it.crew }
            .asNetworkOperationFlowResult()
    }
}

class LoadCastUseCase(private val repository: MovieRepository) : Caseable<Int, List<Cast>> {
    override fun invoke(param: Int): Flow<NetworkOperation<List<Cast>>> {
        return repository.getMovieCredits(movieId = param).map { it.cast }
            .asNetworkOperationFlowResult()
    }
}