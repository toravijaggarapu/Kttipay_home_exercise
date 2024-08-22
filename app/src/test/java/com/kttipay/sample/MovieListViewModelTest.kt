package com.kttipay.sample

import com.kttipay.sample.models.Movie
import com.kttipay.sample.repository.MovieRepo
import com.kttipay.sample.screens.movies.MovieListViewModel
import com.kttipay.sample.screens.movies.NO_DATA_FOUND
import com.kttipay.sample.screens.movies.NowPlayingMovieUiState
import com.kttipay.sample.screens.movies.PopularMovieUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieRepository: MovieRepo
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        movieRepository = mock()
        viewModel = MovieListViewModel(movieRepository)
    }

    @After
    fun tearDown() {
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `getNowPlayingMovies Success with data`() = testScope.runTest {
        val nowPlayingMovies = listOf(
            Movie.getDummyObject()
        )
        whenever(movieRepository.getNowPlayingMovies()).thenReturn(flow { emit(nowPlayingMovies) })

        viewModel.getNowPlayingMovies(testScope)

        advanceUntilIdle()
        verify(movieRepository).getNowPlayingMovies()
        assert(viewModel.nowPlayingMovieUiState.value is NowPlayingMovieUiState.SuccessMoviesNow)
        val successState =
            viewModel.nowPlayingMovieUiState.value as NowPlayingMovieUiState.SuccessMoviesNow
        assert(successState.data == nowPlayingMovies)
    }

    @Test
    fun `getNowPlayingMovies Error`() = testScope.runTest {
        val errorMessage = "Network Error"
        whenever(movieRepository.getNowPlayingMovies()).thenReturn(flow {
            throw Exception(
                errorMessage
            )
        })

        viewModel.getNowPlayingMovies(testScope)

        advanceUntilIdle()
        verify(movieRepository).getNowPlayingMovies()
        assert(viewModel.nowPlayingMovieUiState.value is NowPlayingMovieUiState.ErrorMoviesNow)
        val errorState =
            viewModel.nowPlayingMovieUiState.value as NowPlayingMovieUiState.ErrorMoviesNow
        assert(errorState.message == errorMessage)
    }

    @Test
    fun `getNowPlayingMovies Success but no data`() = testScope.runTest {
        whenever(movieRepository.getNowPlayingMovies()).thenReturn(flow { emit(emptyList()) })

        viewModel.getNowPlayingMovies(testScope)

        advanceUntilIdle()
        verify(movieRepository).getNowPlayingMovies()
        assert(viewModel.nowPlayingMovieUiState.value is NowPlayingMovieUiState.ErrorMoviesNow)
        val errorState =
            viewModel.nowPlayingMovieUiState.value as NowPlayingMovieUiState.ErrorMoviesNow
        assert(errorState.message == NO_DATA_FOUND)
    }

    // Similar test cases for getPopularMovies
    @Test
    fun `getPopularMovies Success with data`() = testScope.runTest {
        val popularMovies = listOf(
            Movie.getDummyObject()
        )
        whenever(movieRepository.getPopularMovies()).thenReturn(flow { emit(popularMovies) })

        viewModel.getPopularMovies(testScope)

        advanceUntilIdle()
        verify(movieRepository).getPopularMovies()
        assert(viewModel.popularMovieUiState.value is PopularMovieUiState.SuccessMoviesPopular)
        val successState =
            viewModel.popularMovieUiState.value as PopularMovieUiState.SuccessMoviesPopular
        assert(successState.data == popularMovies)
    }

    @Test
    fun `getPopularMovies Error`() = testScope.runTest {
        val errorMessage = "Unknown Error"
        whenever(movieRepository.getPopularMovies()).thenReturn(flow { throw Exception(errorMessage) })

        viewModel.getPopularMovies(testScope)

        advanceUntilIdle()
        verify(movieRepository).getPopularMovies()
        assert(viewModel.popularMovieUiState.value is PopularMovieUiState.ErrorMoviesPopular)
        val errorState =
            viewModel.popularMovieUiState.value as PopularMovieUiState.ErrorMoviesPopular
        assert(errorState.message == errorMessage)
    }

    @Test
    fun `getPopularMovies Success but no data`() = testScope.runTest {
        whenever(movieRepository.getPopularMovies()).thenReturn(flow { emit(emptyList()) })

        viewModel.getPopularMovies(testScope)
        advanceUntilIdle()
        verify(movieRepository).getPopularMovies()
        assert(viewModel.popularMovieUiState.value is PopularMovieUiState.ErrorMoviesPopular)
        val errorState =
            viewModel.popularMovieUiState.value as PopularMovieUiState.ErrorMoviesPopular
        assert(errorState.message == NO_DATA_FOUND)
    }
}