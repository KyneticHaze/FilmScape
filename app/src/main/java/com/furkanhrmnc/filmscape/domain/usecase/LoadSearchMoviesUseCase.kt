package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.util.NetworkOperation
import com.furkanhrmnc.filmscape.util.asNetworkOperationFlowResult
import kotlinx.coroutines.flow.Flow

class LoadSearchMoviesUseCase (private val repository: MovieRepository):Caseable<SearchParams, PagingMovie<Movie>> {
    override fun invoke(param: SearchParams): Flow<NetworkOperation<PagingMovie<Movie>>> {
        return repository.searchMovie(
            query = param.query,
            page = param.page
        ).asNetworkOperationFlowResult()
    }
}