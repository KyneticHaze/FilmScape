package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideUseCase(
        movieRepository: MovieRepository
    ): LoadMovieUseCase {
        return LoadMovieUseCase(movieRepository)
    }
}