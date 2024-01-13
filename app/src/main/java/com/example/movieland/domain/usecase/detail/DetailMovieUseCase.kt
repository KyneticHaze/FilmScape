package com.example.movieland.domain.usecase.detail

import com.example.movieland.core.DataStatus
import com.example.movieland.data.remote.dto.detail.DetailDTO
import com.example.movieland.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DetailMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(
        movieId : Int,
        token : String
    ) : Flow<DataStatus<DetailDTO>> = flow {
        try {
            emit(DataStatus.Success(movieRepository.getMovieDetail(movieId, token)))
        } catch (e: HttpException) {
            emit(DataStatus.Error("Http Error found! : ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(DataStatus.Error("IO Error found! : ${e.localizedMessage}"))
        }
    }
}