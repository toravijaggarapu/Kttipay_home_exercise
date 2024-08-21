package com.kttipay.sample.repository

import com.kttipay.sample.models.Movie
import com.kttipay.sample.network.MovieAPIService
import javax.inject.Inject

interface MovieRepo {

    suspend fun getMovies(): List<Movie>

    suspend fun getMovieDetails(id: String): Movie
}

class MovieRepoImpl @Inject constructor(private val apiService: MovieAPIService) : MovieRepo {

    override suspend fun getMovies(): List<Movie> {
        return apiService.getNowPlayingMovies()
    }

    override suspend fun getMovieDetails(id: String): Movie {
        return apiService.getMovieDetails(id)
    }
}