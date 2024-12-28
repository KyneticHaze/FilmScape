package com.furkanhrmnc.filmscape

import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.ui.screen.main.home.HomeViewModel
import com.furkanhrmnc.filmscape.util.MediaType
import com.furkanhrmnc.filmscape.util.Response
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: HomeViewModel
    private val mockRepo: MediaRepository = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(mockRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTrendingMedias updates uiState correctly`() = runTest {

        val mockData = PaginatedData(
            page = 1,
            results = listOf(
                Media(id = 13, title = "Movie 1"),
                Media(id = 26, title = "Movie 2")
            ),
            totalPages = 1,
            totalResults = 1
        )

        whenever(
            mockRepo.getTrendingMovieOrTv(
                "movie",
                "day",
                1
            )
        ).thenReturn(flowOf(Response.Success(mockData)))


        viewModel.loadTrendingMedias()


        val currentState = viewModel.uiState.value
        assertThat(currentState.trendingMedias).isEqualTo(mockData.results)
    }

    @Test
    fun `loadMovie updates uiState correctly`() = runTest {
        val mockData = PaginatedData(
            page = 1,
            results = listOf(Media(id = 13, title = "Movie 1")),
            totalPages = 1,
            totalResults = 1
        )


        whenever(mockRepo.getMovieOrTv(type = "movie", category = "popular", page = 1)).thenReturn(
            flowOf(Response.Success(mockData))
        )

        viewModel.loadMovie()
        val currentState = viewModel.uiState.value
        assertThat(currentState.movie).isEqualTo(mockData.results.first())
    }

    @Test
    fun `loadTvSeries updates uiState correctly`() = runTest {
        val mockData = PaginatedData(
            page = 1,
            results = listOf(Media(id = 13, title = "Tv Series 1", type = MediaType.TV)),
            totalPages = 1,
            totalResults = 1
        )


        whenever(mockRepo.getMovieOrTv(type = "tv", category = "popular", page = 1)).thenReturn(
            flowOf(Response.Success(mockData))
        )

        viewModel.loadTvSeries()
        val currentState = viewModel.uiState.value
        assertThat(currentState.tvSeries).isEqualTo(mockData.results.first())
    }

    @Test
    fun `loadCarousel updates uiState correctly`() = runTest {
        val mockData = PaginatedData(
            page = 1,
            results = listOf(
                Media(id = 12, title = "Tv Series 1", type = MediaType.TV),
                Media(id = 13, title = "Tv Series 2", type = MediaType.TV)
            ),
            totalPages = 1,
            totalResults = 1
        )

        whenever(mockRepo.getMovieOrTv(type = "tv", category = "on_the_air", page = 1)).thenReturn(
            flowOf(Response.Success(mockData))
        )

        viewModel.loadOnTheAirCarousel()
        val currentState = viewModel.uiState.value
        assertThat(currentState.onTheAirCarousel).isEqualTo(mockData.results)
    }
}