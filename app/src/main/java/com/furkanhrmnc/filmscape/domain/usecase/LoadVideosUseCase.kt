package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.Video
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.util.NetworkOperation
import com.furkanhrmnc.filmscape.util.asNetworkOperationFlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoadVideosUseCase(private val repository: MovieRepository) : Caseable<Int, List<Video>> {
    override fun invoke(param: Int): Flow<NetworkOperation<List<Video>>> {
        return repository.getMovieVideos(param).map { it.videos }.asNetworkOperationFlowResult()
    }
}