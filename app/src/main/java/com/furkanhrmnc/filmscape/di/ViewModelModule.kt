package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.ui.screen.details.DetailsViewModel
import com.furkanhrmnc.filmscape.ui.screen.main.MainViewModel
import com.furkanhrmnc.filmscape.ui.screen.main.movies.MediaPager
import com.furkanhrmnc.filmscape.ui.screen.popular.PopularPager
import com.furkanhrmnc.filmscape.ui.screen.popular.PopularViewModel
import com.furkanhrmnc.filmscape.ui.screen.search.SearchMoviesPager
import com.furkanhrmnc.filmscape.ui.screen.search.SearchMoviesViewModel
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarMoviesPager
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarMoviesViewModel
import com.furkanhrmnc.filmscape.util.MediaType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModel'i UI kısmında daha rahat başlatmak ve parametreler'i daha rahat kontrol etmek için yazılan modül.
 *
 * @author Furkan Harmancı
 */
val viewModelModule = module {
    viewModel { params ->
        MainViewModel(
            type = params.get(),
            category = params.get(),
            pager = MediaPager(repo = get())
        )
    }

    viewModel { params ->
        DetailsViewModel(
            id = params.get(),
            repo = get(),
            player = get()
        )
    }

    viewModel { params ->
        SimilarMoviesViewModel(
            pager = SimilarMoviesPager(
                type = MediaType.MOVIE.name,
                movieId = params.get(),
                repo = get()
            )
        )
    }

    viewModel {
        SearchMoviesViewModel(pager = SearchMoviesPager(repo = get()))
    }

    viewModel {
        PopularViewModel(
            pager = PopularPager(repo = get())
        )
    }
}