package com.furkanhrmnc.filmscape.di

import com.furkanhrmnc.filmscape.ui.screen.auth.account.AccountViewModel
import com.furkanhrmnc.filmscape.ui.screen.auth.login.LoginViewModel
import com.furkanhrmnc.filmscape.ui.screen.auth.register.RegisterViewModel
import com.furkanhrmnc.filmscape.ui.screen.details.DetailsViewModel
import com.furkanhrmnc.filmscape.ui.screen.favorite.FavoriteViewModel
import com.furkanhrmnc.filmscape.ui.screen.main.MainViewModel
import com.furkanhrmnc.filmscape.ui.screen.main.medias.MediaPager
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesViewModel
import com.furkanhrmnc.filmscape.ui.screen.person.PersonViewModel
import com.furkanhrmnc.filmscape.ui.screen.person.PersonsPager
import com.furkanhrmnc.filmscape.ui.screen.person.details.PersonDetailsViewModel
import com.furkanhrmnc.filmscape.ui.screen.search.SearchMediasPager
import com.furkanhrmnc.filmscape.ui.screen.search.SearchMediasViewModel
import com.furkanhrmnc.filmscape.ui.screen.settings.SettingsViewModel
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarMediasPager
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarMediasViewModel
import com.furkanhrmnc.filmscape.ui.screen.trending.TrendingPager
import com.furkanhrmnc.filmscape.ui.screen.trending.TrendingViewModel
import com.furkanhrmnc.filmscape.ui.screen.tv.TvViewModel
import com.furkanhrmnc.filmscape.ui.theme.ThemeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModeli UI kısmında daha rahat başlatmak ve parametreleri daha rahat kontrol etmek için yazılan modül.
 *
 * @author Furkan Harmancı
 */
val viewModelModule = module {


    viewModel {
        MainViewModel(repo = get())
    }

    viewModel {
        MoviesViewModel(pager = MediaPager(repo = get()))
    }

    viewModel {
        TvViewModel(pager = MediaPager(repo = get()))
    }

    viewModel {
        DetailsViewModel(repo = get())
    }

    viewModel { params ->
        SimilarMediasViewModel(
            id = params.get(),
            pager = SimilarMediasPager(repo = get())
        )
    }

    viewModel {
        SearchMediasViewModel(pager = SearchMediasPager(repo = get()))
    }

    viewModel {
        TrendingViewModel(pager = TrendingPager(repo = get()))
    }

    viewModel { params ->
        FavoriteViewModel(uid = params.get())
    }

    viewModel {
        PersonViewModel(pager = PersonsPager(repo = get()))
    }

    viewModel { params ->
        PersonDetailsViewModel(
            id = params.get(),
            repo = get()
        )
    }

    viewModel {
        RegisterViewModel(get())
    }

    viewModel {
        LoginViewModel(get())
    }

    viewModel { AccountViewModel() }

    viewModel { ThemeViewModel(storeRepository = get()) }

    viewModel { SettingsViewModel(storeRepository = get()) }
}