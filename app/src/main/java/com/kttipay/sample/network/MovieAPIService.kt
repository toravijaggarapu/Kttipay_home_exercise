package com.kttipay.sample.network

import com.kttipay.sample.models.Movie
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPIService {

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(): List<Movie>

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): List<Movie>

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: String): Movie

}