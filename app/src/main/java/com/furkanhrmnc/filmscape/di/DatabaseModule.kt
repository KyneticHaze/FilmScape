package com.furkanhrmnc.filmscape.di

import android.content.Context
import androidx.room.Room
import com.furkanhrmnc.filmscape.util.Screens.FILM_SCAPE_DB
import com.furkanhrmnc.filmscape.data.local.database.FilmScapeDB
import com.furkanhrmnc.filmscape.data.local.database.MediaDao
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
    ): FilmScapeDB {
        return Room.databaseBuilder(
            context,
            FilmScapeDB::class.java,
            FILM_SCAPE_DB
        ).build()
    }

    @Provides
    @Singleton
    fun provideMediaDao(
        db: FilmScapeDB
    ): MediaDao {
        return db.mediaDao
    }
}