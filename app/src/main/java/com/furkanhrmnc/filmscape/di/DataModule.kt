package com.furkanhrmnc.filmscape.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.furkanhrmnc.filmscape.data.data_source.RemoteDataSource
import com.furkanhrmnc.filmscape.data.network.service.FilmService
import com.furkanhrmnc.filmscape.data.repository.AuthRepositoryImpl
import com.furkanhrmnc.filmscape.data.repository.MediaRepositoryImpl
import com.furkanhrmnc.filmscape.data.repository.StoreRepositoryImpl
import com.furkanhrmnc.filmscape.domain.repository.AuthRepository
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.domain.repository.StoreRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("app_preferences")

/**
 * [MediaRepositoryImpl] singleton şekilde yaratılarak bağımlılık azaltılıyor.
 *
 * @author Furkan Harmancı
 */
val dataModule = module {

    singleOf(::FilmService)

    factory { RemoteDataSource(filmService = get(), dispatcher = Dispatchers) }

    single<MediaRepository> {
        MediaRepositoryImpl(remoteDataSource = get())
    }

    single { FirebaseAuth.getInstance() }

    single<AuthRepository> {
        AuthRepositoryImpl(auth = get())
    }

    single { provideDataStore(context = get<Context>()) }

    single<StoreRepository> {
        StoreRepositoryImpl(dataStore = get())
    }
}

fun provideDataStore(context: Context): DataStore<Preferences> {
    return context.dataStore
}