package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.data.remote.repository.MovieRepositoryImpl
import com.furkanhrmnc.filmscape.data.remote.api.MovieApi
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
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
    fun provideMediaRepository(
        api: MovieApi
    ): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}