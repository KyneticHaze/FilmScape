package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.util.NetworkOperation
import com.furkanhrmnc.filmscape.util.asNetworkOperationFlowResult
import kotlinx.coroutines.flow.Flow

/**
 * [LoadMovieUseCase]'deki şeyler burada da geçerli. Ancak burada sayfalanmış veri yerine [MovieDetails] verisi gelecek.
 *
 * Aynı şekilde [MovieDetails] verisinin flow'lanmış halini burada [NetworkOperation] tipinin içine sokarak gelecek verinin durumunu kontrol ediyoruz.
 *
 * @author Furkan Harmancı
 */
class LoadMovieDetailUseCase(private val repository: MovieRepository) :
    Caseable<Int, MovieDetails> {
    override fun invoke(param: Int): Flow<NetworkOperation<MovieDetails>> =
        repository.getMovieDetails(param).asNetworkOperationFlowResult()
}