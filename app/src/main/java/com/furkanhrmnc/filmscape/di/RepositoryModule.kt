package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.data.local.media.MediaDao
import com.furkanhrmnc.filmscape.data.remote.api.DetailApi
import com.furkanhrmnc.filmscape.data.repository.MediaRepositoryImpl
import com.furkanhrmnc.filmscape.data.remote.api.MediaApi
import com.furkanhrmnc.filmscape.data.repository.DetailRepositoryImpl
import com.furkanhrmnc.filmscape.domain.repository.DetailRepository
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
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
        api: MediaApi,
        dao: MediaDao
    ): MediaRepository {
        return MediaRepositoryImpl(api,dao)
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