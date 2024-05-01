package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.util.NetworkOperation
import com.furkanhrmnc.filmscape.util.asResult
import kotlinx.coroutines.flow.Flow

/**
 * Sınıf olmasına rağmen bir fonksiyon gibi davranacak.
 *
 * [MovieDiff] sınıfının içerdiği kategori ve sayfa değişkenlerini, film verilerini çeken fonksiyonun kategori ve sayfa parametresine yazarak otomatikleştiririz.
 *
 * Bu fonksiyon, Sayfalanmış film veri listesi listesi tipinde bir [NetworkOperation] arayüzü dönecek.
 */
class LoadMovieUseCase(
    private val movieRepository: MovieRepository
): UseCase<MovieDiff, PagingMovie<Movie>> {
    override fun invoke(input: MovieDiff): Flow<NetworkOperation<PagingMovie<Movie>>> {
        return movieRepository.getMovies(
            category = input.category.name.lowercase(),
            page = input.page
        ).asResult()
    }
}