package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.ui.screen.details.DetailsViewModel
import com.furkanhrmnc.filmscape.ui.screen.main.MainViewModel
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesPager
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesViewModel
import com.furkanhrmnc.filmscape.ui.screen.search.SearchMoviesPager
import com.furkanhrmnc.filmscape.ui.screen.search.SearchMoviesViewModel
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarMoviesPager
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModel'i UI kısmında daha rahat başlatmak ve parametreler'i daha rahat kontrol etmek için yazılan modül.
 *
 * @author Furkan Harmancı
 */
val viewModelModule = module {
    viewModel {
        MainViewModel(loadMovieUseCase = get())
    }

    viewModel { params ->
        MoviesViewModel(category = params.get(), pager = MoviesPager(loadMovie = get()))
    }

    viewModel { params ->
        DetailsViewModel(
            movieId = params.get(),
            loadMovieDetailsUseCase = get(),
            loadRecommendationMoviesUseCase = get(),
            loadVideosUseCase = get()
        )
    }

    viewModel { params ->
        SimilarMoviesViewModel(
            pager = SimilarMoviesPager(
                movieId = params.get(),
                loadRecommendationMoviesUseCase = get()
            )
        )
    }

    viewModel {
        SearchMoviesViewModel(
            pager = SearchMoviesPager(
                loadSearchMoviesUseCase = get()
            )
        )
    }
}