package com.furkanhrmnc.filmscape.di

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.furkanhrmnc.filmscape.data.data_source.LocalDataSource
import com.furkanhrmnc.filmscape.data.data_source.RemoteDataSource
import com.furkanhrmnc.filmscape.data.local.MediaDB
import com.furkanhrmnc.filmscape.data.network.service.FilmService
import com.furkanhrmnc.filmscape.data.repository.MediaRepositoryImpl
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.MEDIA_DB
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * [MediaRepositoryImpl] singleton şekilde yaratılarak bağımlılık azaltılıyor.
 *
 * @author Furkan Harmancı
 */
val dataModule = module {

    singleOf(::FilmService)

    single<MediaDB> {
        Room.databaseBuilder(
            get<Context>(), // androidContext() fonksiyonu, her yerde context kullanmamı sağlıyor
            MediaDB::class.java,
            MEDIA_DB
        ).build()
    }

    single { get<MediaDB>().movieDao() }

    single<Player> {
        ExoPlayer.Builder(get<Context>()).build()
    }

    factory { RemoteDataSource(filmService = get(), dispatcher = Dispatchers) }

    factory { LocalDataSource(mediaDao = get(), dispatcher = Dispatchers) }

    single<MediaRepository> {
        MediaRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}