package com.example.movieland.di

import com.example.movieland.data.repository.MediaRepositoryImpl
import com.example.movieland.data.remote.api.MediaApi
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
    fun provideRepository(
        api: MediaApi
    ): MediaRepository {
        return MediaRepositoryImpl(api)
    }
}