package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieDetailsUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        LoadMovieUseCase(repository = get())
    }

    single {
        LoadMovieDetailsUseCase(repository = get())
    }

    single {
        LoadMovieDetailsUseCase(repository = get())
    }

}