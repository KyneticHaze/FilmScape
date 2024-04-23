package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import com.furkanhrmnc.filmscape.util.Resource
import com.furkanhrmnc.filmscape.util.asResult
import kotlinx.coroutines.flow.Flow

class LoadMovieUseCase(
    private val repo: MovieRepository
): UseCase<Param, PaginatedData<Movie>> {
    override fun invoke(param: Param): Flow<Resource<PaginatedData<Movie>>> {
        return repo.getMovies(
            category = param.category.name,
            page = param.page
        ).asResult()
    }
}