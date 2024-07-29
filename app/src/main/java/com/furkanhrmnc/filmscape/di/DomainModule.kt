package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.domain.usecase.LoadCastUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadCrewUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieDetailUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadMovieUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadRecommendationMoviesUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadSearchMoviesUseCase
import com.furkanhrmnc.filmscape.domain.usecase.LoadVideosUseCase
import org.koin.dsl.module

/**
 * İş mantığının bağımlılıklarını azaltmak için yazılan modül
 *
 * @author Furkan Harmancı
 */
val domainModule = module {
    single {
        LoadMovieUseCase(repository = get())
    }

    single {
        LoadMovieDetailUseCase(repository = get())
    }

    single {
        LoadMovieDetailUseCase(repository = get())
    }

    single {
        LoadRecommendationMoviesUseCase(repository = get())
    }

    single {
        LoadSearchMoviesUseCase(repository = get())
    }

    single {
        LoadCastUseCase(repository = get())
    }

    single {
        LoadCrewUseCase(repository = get())
    }

    single {
        LoadVideosUseCase(repository = get())
    }
}