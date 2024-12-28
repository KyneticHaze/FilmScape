package com.furkanhrmnc.filmscape

import com.furkanhrmnc.filmscape.data.data_source.RemoteDataSource
import com.furkanhrmnc.filmscape.data.network.dto.BaseResponse
import com.furkanhrmnc.filmscape.data.network.dto.CreditResponse
import com.furkanhrmnc.filmscape.data.network.dto.MediaDTO
import com.furkanhrmnc.filmscape.data.network.dto.MediaDetailDTO
import com.furkanhrmnc.filmscape.data.network.dto.credit.CastDTO
import com.furkanhrmnc.filmscape.data.network.dto.person.PersonDTO
import com.furkanhrmnc.filmscape.data.network.dto.person.PersonDetailDTO
import com.furkanhrmnc.filmscape.data.network.dto.video.VideoDTO
import com.furkanhrmnc.filmscape.data.network.dto.video.VideoResponse
import com.furkanhrmnc.filmscape.data.repository.MediaRepositoryImpl
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants
import com.furkanhrmnc.filmscape.util.Response
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class MediaRepositoryImplTest {

    private lateinit var repository: MediaRepository
    private lateinit var dataSource: RemoteDataSource
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        dataSource = mock(RemoteDataSource::class.java)
        repository = MediaRepositoryImpl(dataSource)


        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getMovieOrTv_ReturnSuccessResponse_WhenMoviesAreFetched() =
        runTest {
            val mockResponse = BaseResponse(
                page = 1,
                results = listOf(MediaDTO(id = 1, title = "Example Movie")),
                totalPages = 1,
                totalResults = 1
            )
            `when`(dataSource.getMovieOrTv("movie", "popular")).thenReturn(mockResponse)

            val result = repository.getMovieOrTv("movie", "popular", 1).first()

            assertThat(result).isInstanceOf(Response::class.java)
            assertThat((result as Response.Success).data.results).hasSize(1)
            assertThat(result.data.results[0].title).isEqualTo("Example Movie")
        }

    @Test
    fun getMovieDetails_ReturnSuccessResponse_WhenDetailsAreFetched() =
        runTest {
            val mockResponse = MediaDetailDTO(
                id = 123,
                title = "Title",
                overview = "Overview"
            )

            `when`(dataSource.getDetailMovieOrTv(type = "Default Type", 123)).thenReturn(
                mockResponse
            )

            val result = repository.getDetailMediaOrTv("Default Type", 123).first()

            assertThat(result).isInstanceOf(Response::class.java)
            assertThat((result as Response.Success).data.title).isEqualTo("Title")
            assertThat(result.data.overview).isEqualTo("Overview")
        }

    @Test
    fun getRecommendationsMovieOrTv_ReturnSuccessResponse_WhenRecommendationsAreFetched() =
        runTest {
            val mockResponse = BaseResponse(
                page = 1,
                results = listOf(MediaDTO(id = 300, title = "Redemption")),
                totalPages = 1,
                totalResults = 1
            )

            `when`(
                dataSource.getRecommendationsMovieOrTv(
                    type = "Movie",
                    id = 123,
                    page = 1
                )
            ).thenReturn(mockResponse)

            val result =
                repository.getRecommendationsMovieOrTv(
                    type = "Movie",
                    id = 123,
                    page = 1
                ).first()

            assertThat(result).isInstanceOf(Response::class.java)
            assertThat((result as Response.Success).data.results).hasSize(1)
            assertThat(result.data.results[0].title).isEqualTo("Redemption")
        }

    @Test
    fun searchMovieOrTv_ReturnsSuccessResponse_WhenValidQueryIsProvided() =
        runTest {

            val mock = BaseResponse(
                page = 1,
                results = listOf(
                    MediaDTO(id = 200, title = "Rise Of Mummy"),
                    MediaDTO(id = 300, title = "Redemption"),
                    MediaDTO(id = 100, title = "Necromonia") // N ile başlayan bir film
                ),
                totalPages = 1,
                totalResults = 1
            )

            `when`(
                dataSource
                    .searchMovieOrTv(
                        query = "R",
                        type = "Movie",
                        page = 1
                    )
            ).thenReturn(mock)

            val result = repository.searchMovieOrTv(query = "R", type = "Movie", page = 1).first()

            assertThat(result).isInstanceOf(Response::class.java)
            assertThat((result as Response.Success).data.results[1].title).isEqualTo("Redemption")
        }


    @Test
    fun getVideos_ReturnSuccessResponse_WhenVideosAreFetched() = runTest {

        val mockResponse = VideoResponse(
            id = 1,
            results = listOf(
                VideoDTO(
                    id = "22",
                    name = "Redemption Video",
                    site = "Youtube",
                    type = "Trailer",
                    key = "a1b2c3"
                ),
                VideoDTO(
                    id = "33",
                    name = "Necromonia Video",
                    site = "Youtube",
                    type = "Featurette",
                    key = "123456"
                ),
                VideoDTO(
                    id = "44",
                    name = "Rise Of Mummy Video",
                    site = "Youtube",
                    type = "Trailer",
                    key = "abcdef"
                ),
                VideoDTO(
                    id = "55",
                    name = "Inception Video",
                    site = "Youtube",
                    type = "Featurette",
                    key = "xyz789"
                )
            )
        )

        `when`(dataSource.getVideosMovieOrTv(type = "Movie", id = 1)).thenReturn(mockResponse)
        val result = repository.getVideosMovieOrTv(type = "Movie", id = 1).first()
        assertThat((result as Response.Success).data[0].type).isEqualTo("Trailer")
        assertThat(result.data.map { it.key }).containsExactly(
            "a1b2c3",
            "123456",
            "abcdef",
            "xyz789"
        )
    }

    @Test
    fun getMovieCredits_ReturnSuccessResponse_WhenCreditsAreFetched() = runTest {
        val mockResponse =
            CreditResponse(id = 1, cast = listOf(CastDTO(castId = 298, name = "Jason Statham")))

        `when`(dataSource.getCreditsMovieOrTv(type = "Movie", id = 1)).thenReturn(mockResponse)

        val result = repository.getCreditsMovieOrTv(type = "Movie", id = 1).first()

        assertThat(result).isInstanceOf(Response::class.java)
        assertThat((result as Response.Success).data[0].name).isEqualTo("Jason Statham")
        assertThat(result.data[0].castId).isEqualTo(298)
    }

    @Test
    fun getPersons_ReturnSuccessResponse_WhenPersonsAreFetched() = runTest {

        val mock = BaseResponse(
            page = 1,
            results = listOf(
                PersonDTO(id = 345, name = "Naomi Space")
            ),
            totalPages = 1,
            totalResults = 1
        )

        `when`(dataSource.getPopularPersons()).thenReturn(mock)

        val result = repository.getPopularPersons(page = 1).first()
        assertThat(result).isInstanceOf(Response::class.java)
        assertThat((result as Response.Success).data.results[0].name).isEqualTo("Naomi Space")
        assertThat(result.data.results[0].id).isEqualTo(345)
    }

    @Test
    fun getPersonDetail_ReturnSuccessResponse_WhenPersonDetailIsFetched() = runTest {
        val mock = PersonDetailDTO(id = 444, name = "Di Caprio", profilePath = "abc123")

        `when`(dataSource.getPersonDetails(444)).thenReturn(mock)

        val result = repository.getPersonDetails(444).first()
        assertThat(result).isInstanceOf(Response::class.java)
        assertThat((result as Response.Success).data.name).isEqualTo("Di Caprio")
        assertThat(result.data.profilePath).isEqualTo(Constants.imageFormatter("abc123"))
    }
}