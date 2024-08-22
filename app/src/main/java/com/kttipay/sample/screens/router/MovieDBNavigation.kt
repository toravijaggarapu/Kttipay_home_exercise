package com.kttipay.sample.screens.router

sealed class MovieDBNavigation {

    data class MovieDBRoute(val route: String = "MovieDBNavigation") : MovieDBNavigation()

    data class SplashScreen(val destination: String = "SplashScreen") : MovieDBNavigation()
    data class MovieListScreen(val destination: String = "MovieListScreen") : MovieDBNavigation()
    data class MovieDetailScreen(val destination: String = "MovieDetails/{$MOVIE_ID}") : MovieDBNavigation() {

        companion object {
            const val MOVIE_ID = "movieId"
        }

        fun createRoute(movieId: Int) = "MovieDetails/$movieId"
    }
}