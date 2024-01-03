package com.example.movieland.di

import com.example.movieland.data.repository.MovieRepositoryImpl
import com.example.movieland.data.service.ApiService
import com.example.movieland.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        service: ApiService
    ): MovieRepository {
        return MovieRepositoryImpl(service)
    }
}