package com.furkanhrmnc.filmscape.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Uygulamanın ana sınıfı
 *
 * [startKoin] - Koin DI'yı başlatmak için yazılan fonksiyon
 *
 * @author Furkan Harmancı
 */
class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.INFO)
            androidContext(this@MovieApplication)
            modules(appModules)
        }
    }
}