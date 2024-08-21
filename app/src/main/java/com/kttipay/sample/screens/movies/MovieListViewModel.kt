package com.kttipay.sample.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttipay.sample.models.Movie
import com.kttipay.sample.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepo
) : ViewModel() {

    private val _nowPlayingMovieUiState = MutableStateFlow<NowPlayingMovieUiState>(NowPlayingMovieUiState.Loading)
    val nowPlayingMovieUiState = _nowPlayingMovieUiState.asStateFlow()

    private val _popularMovieUiState = MutableStateFlow<PopularMovieUiState>(PopularMovieUiState.Loading)
    val popularMovieUiState = _popularMovieUiState.asStateFlow()

    fun getNowPlayingMovies(scope: CoroutineScope = viewModelScope) {
        scope.launch {
            _nowPlayingMovieUiState.value = NowPlayingMovieUiState.Loading
            movieRepository.getNowPlayingMovies().catch { exception ->
                _nowPlayingMovieUiState.value = NowPlayingMovieUiState.ErrorMoviesNow(exception.message.toString())
            }.collect { data ->
                if (data.isNotEmpty()) {
                    _nowPlayingMovieUiState.value = NowPlayingMovieUiState.SuccessMoviesNow(data)
                } else {
                    _nowPlayingMovieUiState.value = NowPlayingMovieUiState.ErrorMoviesNow(NO_DATA_FOUND)
                }
            }
        }
    }

    fun getPopularMovies(scope: CoroutineScope = viewModelScope) {
        scope.launch {
            _popularMovieUiState.value = PopularMovieUiState.Loading
            movieRepository.getPopularMovies().catch { exception ->
                _popularMovieUiState.value = PopularMovieUiState.ErrorMoviesPopular(exception.message.toString())
            }.collect { data ->
                if (data.isNotEmpty()) {
                    _popularMovieUiState.value = PopularMovieUiState.SuccessMoviesPopular(data)
                } else {
                    _popularMovieUiState.value = PopularMovieUiState.ErrorMoviesPopular(NO_DATA_FOUND)
                }
            }
        }
    }
}

const val NO_DATA_FOUND = "No Data Found"

sealed class NowPlayingMovieUiState {
    object Loading : NowPlayingMovieUiState()
    data class SuccessMoviesNow(val data: List<Movie>) : NowPlayingMovieUiState()
    data class ErrorMoviesNow(val message: String) : NowPlayingMovieUiState()
}

sealed class PopularMovieUiState {
    object Loading : PopularMovieUiState()
    data class SuccessMoviesPopular(val data: List<Movie>) : PopularMovieUiState()
    data class ErrorMoviesPopular(val message: String) : PopularMovieUiState()
}