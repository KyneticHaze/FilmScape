package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.ui.screen.details.DetailsViewModel
import com.furkanhrmnc.filmscape.ui.screen.main.MainViewModel
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesPager
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(loadMovieUseCase = get())
    }

    viewModel {params ->
        MoviesViewModel(category = params.get(), pager = MoviesPager(loadMovie = get()))
    }

    viewModel {params ->
        DetailsViewModel(movieId = params.get(), loadMovieDetailsUseCase = get())
    }
}