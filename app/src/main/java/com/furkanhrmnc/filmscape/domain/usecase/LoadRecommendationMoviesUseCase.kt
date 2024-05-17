package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.util.NetworkOperation
import com.furkanhrmnc.filmscape.util.asNetworkOperationFlowResult
import kotlinx.coroutines.flow.Flow

/**
 * [RecommendationMovieParams] - ile ID bazlı sayfalanmış verileri getirmeyi kolaylaştıracağız.
 *
 * @author Furkan Harmancı
 */
class LoadRecommendationMoviesUseCase(private val repository: MovieRepository) :
    Caseable<RecommendationMovieParams, PagingMovie<Movie>> {
    override fun invoke(param: RecommendationMovieParams): Flow<NetworkOperation<PagingMovie<Movie>>> {
        return repository.getMovieRecommendations(
            movieId = param.id,
            page = param.page
        ).asNetworkOperationFlowResult()
    }
}