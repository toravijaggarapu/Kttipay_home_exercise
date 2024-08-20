package com.kttipay.sample.screens.router

sealed class MovieDBNavigation {
    data class SplashScreen(val destination: String = "SplashScreen") : MovieDBNavigation()
    data class MovieListScreen(val destination: String = "MovieListScreen") : MovieDBNavigation()
}