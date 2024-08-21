package com.kttipay.sample.network.dto

import com.google.gson.annotations.SerializedName
import com.kttipay.sample.BuildConfig
import com.kttipay.sample.models.Movie
import com.kttipay.sample.models.MovieInfo
import java.text.DecimalFormat

data class MovieResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: List<MovieData> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)

data class MovieData(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("genre_ids") var genreIds: List<Int> = arrayListOf(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null
)

fun MovieResponse.getMovies() = results.map { it.toMovie() }

fun MovieData.toMovie() = Movie(
    movieId = this.id ?: 0,
    title = this.title ?: "",
    backDropPath = backdropPath ?: "",
    posterPath = posterPath ?: "",
    ratting = DecimalFormat("#.#").format(voteAverage ?: 0.0)
)

data class MovieDetails(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("budget") var budget: Int? = null,
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("imdb_id") var imdbId: String? = null,
    @SerializedName("origin_country") var originCountry: ArrayList<String> = arrayListOf(),
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("revenue") var revenue: Int? = null,
    @SerializedName("runtime") var runtime: Int? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null
)

fun MovieDetails.toMovieInfo() = MovieInfo(
    title = this.originalTitle ?: "",
    releaseDate = this.releaseDate ?: "",
    ratting = DecimalFormat("#.#").format(voteAverage ?: 0.0),
    backDropPath = this.backdropPath ?: "",
    overview = this.overview ?: "",
    runTime = this.runtime ?: 0,
)