package com.kttipay.sample.models

import com.kttipay.sample.BuildConfig
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class Movie(
    val title: String,
    val ratting: String,
    val backDropPath: String,
    val movieId: Int,
    val posterPath: String
) {

    val backDropFullImageUrl: String
        get() = BuildConfig.IMAGE_BASE_URL + "w300" + backDropPath

    val posterFullImageUrl: String
        get() = BuildConfig.IMAGE_BASE_URL + "w200" + posterPath

    companion object {
        fun getDummyObject() = Movie(
            title = "Movie name",
            ratting = "3.5",
            backDropPath = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrF",
            movieId = 12345,
            posterPath = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrF"
        )
    }
}

data class MovieInfo(
    val title: String,
    val releaseDate: String,
    val ratting: String,
    val backDropPath: String,
    val overview: String,
    val runTime: Int,
) {
    val backDropFullImageUrl: String
        get() = BuildConfig.IMAGE_BASE_URL + "w400" + backDropPath

    val displayDateFormat: String
        get() = releaseDate.formatFrom(RESPONSE_DATE_FORMAT).formatTo(DISPLAY_DATE_FORMAT)

    val runtimeDisplay: String
        get() = "${runTime / 60} Hours ${if(runTime % 60 == 0) "" else "${runTime % 60} Minutes"}"

    companion object {
        fun getDummyObject() = MovieInfo(
            title = "Movie name",
            releaseDate = "2024-07-24",
            ratting = "3.5",
            backDropPath = "",
            overview = "Hello Overview world",
            runTime = 120
        )
    }
}

const val RESPONSE_DATE_FORMAT = "yyyy-MM-dd"
const val DISPLAY_DATE_FORMAT = "dd MMM yyyy"

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun String.formatFrom(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): Date {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.parse(this) ?: Calendar.getInstance().time
}