package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.data.remote.api.DetailApi
import com.furkanhrmnc.filmscape.data.remote.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideApiClient(
        interceptor : Interceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMediaApi(
        retrofit: Retrofit
    ) : MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailApi(
        retrofit: Retrofit
    ) : DetailApi {
        return retrofit.create(DetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGenreApi(
        retrofit: Retrofit
    ) : GenreApi {
        return retrofit.create(GenreApi::class.java)
    }
}