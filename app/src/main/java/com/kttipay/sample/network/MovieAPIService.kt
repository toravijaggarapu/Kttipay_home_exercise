package com.kttipay.sample.network

import com.kttipay.sample.network.dto.MovieDetails
import com.kttipay.sample.network.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPIService {

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieResponse

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): MovieDetails

}