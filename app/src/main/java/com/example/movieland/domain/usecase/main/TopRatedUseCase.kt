package com.example.movieland.domain.usecase.main

import com.example.movieland.core.DataStatus
import com.example.movieland.data.remote.dto.commonDto.MovieResponse
import com.example.movieland.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopRatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(
        lang: String,
        page : Int,
        token : String
    ) : Flow<DataStatus<MovieResponse>> = flow {
        try {
            emit(DataStatus.Success(movieRepository.getTopRatedMovies(lang, page, token)))
        } catch (e: HttpException) {
            emit(DataStatus.Error("Http Error found! : ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(DataStatus.Error("IO Error found! : ${e.localizedMessage}"))
        }
    }
}