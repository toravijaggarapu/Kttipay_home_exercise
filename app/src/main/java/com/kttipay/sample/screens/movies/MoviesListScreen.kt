package com.kttipay.sample.screens.movies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kttipay.sample.R
import com.kttipay.sample.models.Movie
import com.kttipay.sample.screens.CommonProgress
import com.kttipay.sample.screens.router.MovieDBNavigation
import com.kttipay.sample.ui.theme.KttipayTextStyle.Eighteen600Black
import com.kttipay.sample.ui.theme.KttipayTextStyle.Thirteen700White
import com.kttipay.sample.ui.theme.KttipayTextStyle.Sixteen400Black
import com.kttipay.sample.ui.theme.KttipayTextStyle.Twenty400White
import com.kttipay.sample.ui.theme.KttipayTextStyle.TwentyTwo500Black
import com.kttipay.sample.ui.theme.MarginFifteen
import com.kttipay.sample.ui.theme.MarginFive
import com.kttipay.sample.ui.theme.MarginTen
import com.kttipay.sample.ui.theme.MarginThree
import com.kttipay.sample.ui.theme.MarginThreeHundred
import com.kttipay.sample.ui.theme.MarginTwenty
import com.kttipay.sample.ui.theme.MarginTwoFifty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MoviesListScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: MovieListViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getNowPlayingMovies()
        viewModel.getPopularMovies()
    }

    MoviesView(
        nowPlayingMovieState = viewModel.nowPlayingMovieUiState,
        popularMovieState = viewModel.popularMovieUiState
    ) {
        navController.navigate(MovieDBNavigation.MovieDetailScreen().destination)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
private fun MoviesView(
    nowPlayingMovieState: StateFlow<NowPlayingMovieUiState> = MutableStateFlow(
        NowPlayingMovieUiState.ErrorMoviesNow(stringResource(id = R.string.no_movies_found))
    ),
    popularMovieState: StateFlow<PopularMovieUiState> = MutableStateFlow(
        PopularMovieUiState.ErrorMoviesPopular(stringResource(id = R.string.no_movies_found))
    ),
    navigateToMovieDetails: (Int) -> Unit = {}
) {

    val nowPlayingMovieUiState = nowPlayingMovieState.collectAsState()
    val popularMovieUiState = popularMovieState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MarginTen)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_menu), contentDescription = "Menu"
            )

            Text(
                text = "Kttipay",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = MarginThree),
                style = TwentyTwo500Black
            )

            Spacer(modifier = Modifier.width(44.dp))
        }

        Text(
            text = "Now Showing",
            style = Eighteen600Black,
            modifier = Modifier.padding(start = MarginFifteen, top = MarginTen)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MarginTwoFifty),
            contentAlignment = Alignment.Center
        ) {
            nowPlayingMovieUiState.value.let { state ->

                when (state) {
                    NowPlayingMovieUiState.Loading -> {
                        CommonProgress()
                    }

                    is NowPlayingMovieUiState.SuccessMoviesNow -> {
                        val moviesList = state.data
                        val pagerState = rememberPagerState { moviesList.size }
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth()
                        ) { page ->
                            NowShowingItem(moviesList[page], navigateToMovieDetails)
                        }
                    }

                    is NowPlayingMovieUiState.ErrorMoviesNow -> {
                        NoMoviesFound(state.message)
                    }
                }
            }
        }


        Text(
            text = "Popular Movies",
            style = Eighteen600Black,
            modifier = Modifier.padding(start = MarginFifteen, top = MarginTen)
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            popularMovieUiState.value.let { state ->

                when (state) {
                    PopularMovieUiState.Loading -> {
                        CommonProgress()
                    }

                    is PopularMovieUiState.SuccessMoviesPopular -> {
                        val moviesList = state.data
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            items(moviesList) { movieObj ->
                                PopularMovieItem(movieObj, navigateToMovieDetails)
                            }
                        }
                    }

                    is PopularMovieUiState.ErrorMoviesPopular -> {
                        NoMoviesFound(state.message)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Preview
private fun NowShowingItem(
    movie: Movie = Movie.getDummyObject(),
    navigateToMovieDetails: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(MarginTwoFifty)
            .padding(MarginTwenty),
        elevation = CardDefaults.cardElevation(defaultElevation = MarginFive),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(MarginFifteen)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            GlideImage(
                model = movie.backDropFullImageUrl,
                contentDescription = movie.title,
                loading = placeholder(R.drawable.ic_loading),
                failure = placeholder(R.drawable.ic_error),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToMovieDetails(movie.movieId) },
                contentScale = ContentScale.FillBounds
            )

            Column {
                Row(
                    modifier = Modifier
                        .background(Color(0x60000000))
                        .padding(MarginFive)
                ) {
                    Text(text = movie.ratting, style = Thirteen700White)
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = movie.ratting,
                        modifier = Modifier.padding(start = MarginFive),
                    )
                }

                Text(
                    text = movie.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x60000000))
                        .padding(MarginFifteen),
                    style = Twenty400White
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Preview
private fun PopularMovieItem(
    movie: Movie = Movie.getDummyObject(),
    navigateToMovieDetails: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(MarginThreeHundred)
            .padding(MarginTen),
        elevation = CardDefaults.cardElevation(defaultElevation = MarginFive),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(MarginTwenty)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            GlideImage(
                model = movie.posterFullImageUrl,
                contentDescription = movie.title,
                loading = placeholder(R.drawable.ic_loading),
                failure = placeholder(R.drawable.ic_error),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToMovieDetails(movie.movieId) },
                contentScale = ContentScale.FillBounds
            )

            Row(
                modifier = Modifier
                    .background(Color(0x60000000))
                    .padding(MarginTen)
                    .align(Alignment.BottomEnd)
            ) {
                Text(text = movie.ratting, style = Thirteen700White)
                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = movie.ratting,
                    modifier = Modifier.padding(start = MarginFive),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NoMoviesFound(message: String = stringResource(R.string.no_movies_found)) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(MarginFifteen)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_records),
            contentDescription = stringResource(R.string.no_records)
        )

        Text(text = message, style = Sixteen400Black)
    }
}