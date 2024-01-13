package com.example.movieland.domain.usecase.main

import com.example.movieland.core.DataStatus
import com.example.movieland.data.remote.dto.commonDto.MovieDTO
import com.example.movieland.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(
        query: String,
        page : Int,
        token : String
    ) : Flow<DataStatus<List<MovieDTO>>> = flow {
        try {
            movieRepository.getSearchMovie(query, page, token).movies?.let {
                emit(DataStatus.Success(it))
            }
        } catch (e: HttpException) {
            emit(DataStatus.Error("Http Error found! : ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(DataStatus.Error("IO Error found! : ${e.localizedMessage}"))
        }
    }
}