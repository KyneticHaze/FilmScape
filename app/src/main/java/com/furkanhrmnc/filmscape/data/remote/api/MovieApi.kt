package com.furkanhrmnc.filmscape.data.remote.api

import com.furkanhrmnc.filmscape.data.remote.dto.details.MovieDetailsDTO
import com.furkanhrmnc.filmscape.data.remote.dto.details.credit.CreditResponse
import com.furkanhrmnc.filmscape.data.remote.dto.movie.MovieResponse
import com.furkanhrmnc.filmscape.data.remote.dto.videos.VideoResponse
import com.furkanhrmnc.filmscape.util.ApiConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit kütüphanesi kullanarak Tmdb api'den veri çekmek için yazdığımız fonksiyonları içeren arayüzdür.
 *
 * [getMovies] - Kategorilerine göre 20'li şekilde sayfalanmış liste sunar.
 *
 * [getMovieDetails] - Film detaylarını sunar.
 *
 * [searchMovie] - Film aramak için yazdığım fonksiyondur. Bu fonksiyon da 20'li film sunar.
 *
 * @author Furkan Harmancı
 */
interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
    ): MovieDetailsDTO

    @GET("movie/{id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("id") id: Int,
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

    /**
     * id bazlı filmin oyuncularını sunar.
     *
     * @param id benzersiz film id'si
     */
    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
    ): CreditResponse

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
    ): VideoResponse
}