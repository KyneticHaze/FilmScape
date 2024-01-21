package com.example.movieland.di

import com.example.movieland.data.remote.api.DetailApi
import com.example.movieland.data.remote.api.GenreApi
import com.example.movieland.data.remote.api.MediaApi
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
    fun provideMediaApi(
        client: OkHttpClient
    ) : MediaApi {
        return Retrofit.Builder()
            .baseUrl(MediaApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MediaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailApi(
        client: OkHttpClient
    ) : DetailApi {
        return Retrofit.Builder()
            .baseUrl(MediaApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGenreApi(
        client: OkHttpClient
    ) : GenreApi {
        return Retrofit.Builder()
            .baseUrl(MediaApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GenreApi::class.java)
    }
}