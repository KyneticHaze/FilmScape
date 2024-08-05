package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.util.NetworkOperation
import com.furkanhrmnc.filmscape.util.asNetworkOperationFlowResult
import kotlinx.coroutines.flow.Flow

/**
 * Sınıf olmasına rağmen bir fonksiyon gibi davranacak.
 *
 * [CategoryMovieParams] sınıfının içerdiği kategori ve sayfa değişkenlerini, film verilerini çeken fonksiyonun kategori ve sayfa parametresine yazarak otomatikleştiririz.
 *
 * Bu fonksiyon, Sayfalanmış film veri listesi listesi tipinde bir [NetworkOperation] arayüzü dönecek.
 *
 * @author Furkan Harmancı
 */
class LoadMovieUseCase(private val repository: MovieRepository) :
    Caseable<CategoryMovieParams, PagingMovie<Movie>> {
    override fun invoke(param: CategoryMovieParams): Flow<NetworkOperation<PagingMovie<Movie>>> {
        return repository.getMovies(
            category = param.category.name.lowercase(),
            page = param.page
        ).asNetworkOperationFlowResult()
    }
}