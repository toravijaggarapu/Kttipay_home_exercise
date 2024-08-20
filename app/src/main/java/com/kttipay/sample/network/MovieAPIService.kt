package com.kttipay.sample.network

import com.kttipay.sample.models.Movie
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPIService {

    @GET("/api/movies")
    suspend fun getMovies(): List<Movie>

    @GET("/api/movies/details=(movieId)")
    suspend fun getMovieDetails(@Path("movieId") movieId: String): Movie

}