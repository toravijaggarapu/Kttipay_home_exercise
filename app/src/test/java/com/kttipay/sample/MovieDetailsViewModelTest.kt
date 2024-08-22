package com.kttipay.sample

import com.kttipay.sample.models.MovieInfo
import com.kttipay.sample.repository.MovieRepo
import com.kttipay.sample.screens.movie_details.MovieDetailsUiState
import com.kttipay.sample.screens.movie_details.MovieDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var repository: MovieRepo
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        repository = mock()
        viewModel = MovieDetailsViewModel(repository)
    }

    @After
    fun tearDown() {
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `getMovieDetails Success`() = testScope.runTest {
        val movieId = 1
        val movieDetails = MovieInfo.getDummyObject()

        whenever(repository.getMovieDetails(movieId)).thenReturn(flow { emit(movieDetails) })

        viewModel.getMovieDetails(movieId, testScope)

        advanceUntilIdle()
        verify(repository).getMovieDetails(movieId)
        assert(viewModel.movieDetailsUiState.value is MovieDetailsUiState.SuccessMoviesDetails)
        val successState =
            viewModel.movieDetailsUiState.value as MovieDetailsUiState.SuccessMoviesDetails
        assert(successState.data == movieDetails)
    }

    @Test
    fun `getMovieDetails Error`() = testScope.runTest {
        val movieId = 1
        val errorMessage = "Something went wrong."
        whenever(repository.getMovieDetails(movieId)).thenReturn(flow { throw Exception(errorMessage) })

        viewModel.getMovieDetails(movieId, testScope)

        advanceUntilIdle()
        verify(repository).getMovieDetails(movieId)
        assert(viewModel.movieDetailsUiState.value is MovieDetailsUiState.ErrorMoviesDetails)
        val errorState =
            viewModel.movieDetailsUiState.value as MovieDetailsUiState.ErrorMoviesDetails
        assert(errorState.message == errorMessage)
    }
}