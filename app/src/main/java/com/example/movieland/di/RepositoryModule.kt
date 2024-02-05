package com.example.movieland.di

import com.example.movieland.data.local.MediaDB
import com.example.movieland.data.remote.api.DetailApi
import com.example.movieland.data.repository.MediaRepositoryImpl
import com.example.movieland.data.remote.api.MediaApi
import com.example.movieland.data.repository.DetailRepositoryImpl
import com.example.movieland.domain.repository.DetailRepository
import com.example.movieland.domain.repository.MediaRepository
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
        db: MediaDB
    ): MediaRepository {
        return MediaRepositoryImpl(api, db.mediaDao())
    }

    @Provides
    @Singleton
    fun provideDetailRepository(
        api: DetailApi,
        db: MediaDB
    ): DetailRepository {
        return DetailRepositoryImpl(api, db.mediaDao())
    }
}