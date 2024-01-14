package com.example.movieland.di

import com.example.movieland.data.repository.MovieRepositoryImpl
import com.example.movieland.data.remote.service.ApiService
import com.example.movieland.domain.repository.MovieRepository
import com.example.movieland.domain.usecase.detail.DetailMovieUseCase
import com.example.movieland.domain.usecase.detail.DetailUseCase
import com.example.movieland.domain.usecase.detail.MovieCreditsUseCase
import com.example.movieland.domain.usecase.detail.MovieImageUseCase
import com.example.movieland.domain.usecase.detail.MovieSimilarUseCase
import com.example.movieland.domain.usecase.main.MainUseCase
import com.example.movieland.domain.usecase.main.NowPlayingUseCase
import com.example.movieland.domain.usecase.main.PopularUseCase
import com.example.movieland.domain.usecase.main.SearchMovieUseCase
import com.example.movieland.domain.usecase.main.TopRatedUseCase
import com.example.movieland.domain.usecase.main.UpComingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        service: ApiService
    ): MovieRepository {
        return MovieRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideMainUseCase(
        repository : MovieRepository
    ) : MainUseCase {
        return MainUseCase(
            nowPlayingUseCase = NowPlayingUseCase(repository),
            upComingUseCase = UpComingUseCase(repository),
            popularUseCase = PopularUseCase(repository),
            topRatedUseCase = TopRatedUseCase(repository),
            searchMovieUseCase = SearchMovieUseCase(repository)
        )
    }
    @Provides
    @Singleton
    fun provideDetailUseCase(
        repository : MovieRepository
    ) : DetailUseCase {
        return DetailUseCase(
            movieImageUseCase = MovieImageUseCase(repository),
            movieSimilarUseCase = MovieSimilarUseCase(repository),
            detailMovieUseCase = DetailMovieUseCase(repository),
            movieCreditsUseCase = MovieCreditsUseCase(repository)
        )
    }
}