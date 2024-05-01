package com.furkanhrmnc.filmscape.data.remote.api

import com.furkanhrmnc.filmscape.data.remote.dto.media.MovieResponse
import com.furkanhrmnc.filmscape.util.ApiConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit kütüphanesi kullanarak Tmdb api'den veri çekmek için yazdığımız fonksiyonları içeren arayüzdür.
 *
 * [getMovies] - Kategorilerine göre 20'li şekilde sayfalanmış liste sunar.
 *
 * [searchMovie] - Film aramak için yazdığım fonksiyondur. Bu fonksiyon da 20'li film sunar.
 */
interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
    ): MovieResponse

    /**
     * @param query Yapılacak sorguyu bu parametre ile belirleriz.
     */
    @GET("search/multi")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
    ): MovieResponse
}