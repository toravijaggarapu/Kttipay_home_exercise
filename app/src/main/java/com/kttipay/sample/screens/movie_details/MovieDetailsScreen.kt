package com.kttipay.sample.screens.movie_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kttipay.sample.R
import com.kttipay.sample.models.MovieInfo
import com.kttipay.sample.screens.CommonProgress
import com.kttipay.sample.screens.movies.NoMoviesFound
import com.kttipay.sample.ui.theme.KttipayTextStyle.Eighteen600White
import com.kttipay.sample.ui.theme.KttipayTextStyle.Forty600White
import com.kttipay.sample.ui.theme.KttipayTextStyle.Sixteen400White
import com.kttipay.sample.ui.theme.KttipayTextStyle.Thirteen700White
import com.kttipay.sample.ui.theme.KttipayTextStyle.Thirty400White
import com.kttipay.sample.ui.theme.KttipayTextStyle.Twenty400White
import com.kttipay.sample.ui.theme.KttipayTextStyle.TwentyFour700White
import com.kttipay.sample.ui.theme.MarginFifteen
import com.kttipay.sample.ui.theme.MarginFifty
import com.kttipay.sample.ui.theme.MarginFive
import com.kttipay.sample.ui.theme.MarginTen
import com.kttipay.sample.ui.theme.MarginTwenty
import com.kttipay.sample.ui.theme.MarginTwoFifty

@Composable
fun MovieDetailsScreen(
    movieId: Int = 748783, viewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val movieDetailsState = viewModel.movieDetailsUiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.getMovieDetails(movieId) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xD8010101)),
        contentAlignment = Alignment.Center
    ) {
        movieDetailsState.value.let {
            when (it) {
                MovieDetailsUiState.Loading -> {
                    CommonProgress()
                }

                is MovieDetailsUiState.SuccessMoviesDetails -> {
                    MovieDetailsView(it.data)
                }

                is MovieDetailsUiState.ErrorMoviesDetails -> {
                    NoMoviesFound(it.message)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Preview(showBackground = true)
private fun MovieDetailsView(movieDetails: MovieInfo = MovieInfo.getDummyObject()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xD8010101))
    ) {

        Text(
            text = movieDetails.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MarginTwenty),
            style = TwentyFour700White,
            textAlign = TextAlign.Center
        )

        Text(
            text = movieDetails.displayDateFormat,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MarginFive),
            style = Twenty400White,
            textAlign = TextAlign.Center
        )

        GlideImage(
            model = movieDetails.backDropFullImageUrl,
            contentDescription = movieDetails.title,
            loading = placeholder(R.drawable.ic_movie_bg),
            failure = placeholder(R.drawable.ic_movie_bg),
            modifier = Modifier
                .fillMaxWidth()
                .height(MarginTwoFifty)
                .padding(top = MarginFifteen),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            Text(
                text = stringResource(R.string.movie_db_ratting),
                modifier = Modifier.padding(
                    start = MarginFifteen,
                    top = MarginFifteen
                ),
                style = Thirteen700White
            )

            Row(modifier = Modifier.padding(top = MarginTen)) {

                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = movieDetails.ratting,
                    modifier = Modifier
                        .padding(start = MarginFifteen)
                        .height(MarginFifty)
                        .width(MarginFifty)
                )

                Text(
                    text = movieDetails.ratting,
                    modifier = Modifier.padding(start = MarginFifteen, top = MarginTen),
                    style = Forty600White
                )

                Text(
                    text = "/10",
                    style = Thirty400White,
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }

            Text(
                text = stringResource(R.string.overview),
                modifier = Modifier.padding(start = MarginFifteen, top = MarginTwenty),
                style = Eighteen600White
            )

            Text(
                text = movieDetails.overview,
                modifier = Modifier.padding(start = MarginFifteen, top = MarginFive),
                style = Sixteen400White
            )

            Text(
                text = stringResource(R.string.run_time),
                modifier = Modifier.padding(
                    start = MarginFifteen,
                    top = MarginTwenty,
                    end = MarginTen
                ),
                style = Eighteen600White
            )

            Text(
                text = movieDetails.runtimeDisplay,
                style = Twenty400White,
                modifier = Modifier.padding(start = MarginFifteen, top = MarginFive)
            )
        }
    }
}