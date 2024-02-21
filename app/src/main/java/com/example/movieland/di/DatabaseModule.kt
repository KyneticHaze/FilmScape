package com.example.movieland.di

import android.content.Context
import androidx.room.Room
import com.example.movieland.core.Constants.MEDIA_DB
import com.example.movieland.data.local.MediaDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMediaDatabase(
        @ApplicationContext context: Context
    ): MediaDB {
        return Room.databaseBuilder(
            context,
            MediaDB::class.java,
            MEDIA_DB
        ).build()
    }
}