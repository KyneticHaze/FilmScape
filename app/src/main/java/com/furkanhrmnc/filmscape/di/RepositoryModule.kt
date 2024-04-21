package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.data.local.database.MediaDao
import com.furkanhrmnc.filmscape.data.remote.api.DetailApi
import com.furkanhrmnc.filmscape.data.remote.repository.MovieRepositoryImpl
import com.furkanhrmnc.filmscape.data.remote.api.MovieApi
import com.furkanhrmnc.filmscape.data.remote.repository.DetailRepositoryImpl
import com.furkanhrmnc.filmscape.domain.repository.DetailRepository
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
        api: MovieApi,
        dao: MediaDao
    ): MovieRepository {
        return MovieRepositoryImpl(api,dao)
    }

    @Provides
    @Singleton
    fun provideDetailRepository(
        api: DetailApi,
        dao: MediaDao
    ): DetailRepository {
        return DetailRepositoryImpl(api,dao)
    }
}