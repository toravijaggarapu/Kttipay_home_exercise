package com.kttipay.sample.screens.router

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kttipay.sample.screens.movies.MoviesListScreen
import com.kttipay.sample.screens.SplashScreen
import com.kttipay.sample.screens.movie_details.MovieDetailsScreen

@Composable
fun MovieDbNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MovieDBNavigation.SplashScreen().destination
    ) {
        composable(MovieDBNavigation.SplashScreen().destination) {
            SplashScreen(navController)
        }

        composable(MovieDBNavigation.MovieListScreen().destination) {
            MoviesListScreen(navController)
        }

        composable(MovieDBNavigation.MovieDetailScreen().destination) {
            MovieDetailsScreen()
        }
    }
}