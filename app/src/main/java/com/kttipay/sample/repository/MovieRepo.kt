package com.kttipay.sample.repository

import com.kttipay.sample.models.Movie
import com.kttipay.sample.models.MovieInfo
import com.kttipay.sample.network.MovieAPIService
import com.kttipay.sample.network.dto.getMovies
import com.kttipay.sample.network.dto.toMovieInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface MovieRepo {

    fun getNowPlayingMovies(): Flow<List<Movie>>

    fun getPopularMovies(): Flow<List<Movie>>

    fun getMovieDetails(id: Int): Flow<MovieInfo>
}

class MovieRepoImpl @Inject constructor(private val apiService: MovieAPIService) : MovieRepo {

    override fun getNowPlayingMovies(): Flow<List<Movie>> = flow {
        val response = apiService.getNowPlayingMovies()
        emit(response.getMovies())
    }.flowOn(Dispatchers.IO)

    override fun getPopularMovies(): Flow<List<Movie>> = flow {
        val response = apiService.getPopularMovies()
        emit(response.getMovies())
    }.flowOn(Dispatchers.IO)

    override fun getMovieDetails(id: Int): Flow<MovieInfo> = flow {
        val response = apiService.getMovieDetails(id)
        emit(response.toMovieInfo())
    }.flowOn(Dispatchers.IO)
}