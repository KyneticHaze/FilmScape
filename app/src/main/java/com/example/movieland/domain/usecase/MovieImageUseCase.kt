package com.example.movieland.domain.usecase

import com.example.movieland.core.DataStatus
import com.example.movieland.data.remote.dto.image.ImageResponse
import com.example.movieland.data.remote.dto.image.Poster
import com.example.movieland.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieImageUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(
        movieId : Int,
        page : Int,
        token : String
    ) : Flow<DataStatus<List<Poster>>> = flow {
        try {
            movieRepository.getMovieImage(movieId, page, token).posters?.let {
                emit(DataStatus.Success(it))
            }
        } catch (e: HttpException) {
            emit(DataStatus.Error("Http Error found! : ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(DataStatus.Error("IO Error found! : ${e.localizedMessage}"))
        }
    }
}