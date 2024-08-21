package com.kttipay.sample.screens.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttipay.sample.models.MovieInfo
import com.kttipay.sample.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieRepo) : ViewModel() {

    private val _movieDetailsUiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val movieDetailsUiState = _movieDetailsUiState.asStateFlow()

    fun getMovieDetails(movieId: Int, scope: CoroutineScope = viewModelScope) {
        scope.launch {
            _movieDetailsUiState.value = MovieDetailsUiState.Loading
            repository.getMovieDetails(movieId).catch { exception ->
                _movieDetailsUiState.value = MovieDetailsUiState.ErrorMoviesDetails(exception.message.toString())
            }.collect { data ->
                _movieDetailsUiState.value = MovieDetailsUiState.SuccessMoviesDetails(data)
            }
        }
    }
}

sealed class MovieDetailsUiState {
    object Loading : MovieDetailsUiState()
    data class SuccessMoviesDetails(val data: MovieInfo) : MovieDetailsUiState()
    data class ErrorMoviesDetails(val message: String) : MovieDetailsUiState()
}