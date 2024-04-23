package com.furkanhrmnc.filmscape.data.remote.repository

import com.furkanhrmnc.filmscape.data.remote.api.MovieApi
import com.furkanhrmnc.filmscape.data.remote.dto.media.PaginatedDataDTO
import com.furkanhrmnc.filmscape.data.remote.dto.media.toModel
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class MovieRepositoryImpl(
    private val api: MovieApi,
) : MovieRepository {
    override fun getMovies(category: String, page: Int): Flow<PaginatedData<Movie>> = flow {
        api.getMovies(
            category = category,
            page = page
        ).also { response -> emit(value = response) }
    }.map(PaginatedDataDTO::toModel)

    override fun searchMovie(query: String, page: Int): Flow<PaginatedData<Movie>> = flow {
        api.searchMovie(
            query = query,
            page = page
        ).also { response -> emit(value = response)}
    }.map(PaginatedDataDTO::toModel)
}