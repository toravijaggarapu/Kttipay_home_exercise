package com.kttipay.sample.screens.movies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kttipay.sample.R
import com.kttipay.sample.models.Movie
import com.kttipay.sample.ui.theme.KttipayTextStyle.Eighteen600Black
import com.kttipay.sample.ui.theme.KttipayTextStyle.TwentyTwo500Black
import com.kttipay.sample.ui.theme.KttipayTextStyle.Twenty400White
import com.kttipay.sample.ui.theme.MarginFifteen
import com.kttipay.sample.ui.theme.MarginFive
import com.kttipay.sample.ui.theme.MarginTen
import com.kttipay.sample.ui.theme.MarginThree
import com.kttipay.sample.ui.theme.MarginThreeHundred
import com.kttipay.sample.ui.theme.MarginTwenty


@Composable
fun MoviesListScreen(navController: NavController) {
    MoviesView()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
private fun MoviesView(list: List<Movie> = getDummyMovies()) {

    val pagerState = rememberPagerState { list.size }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MarginTen)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu"
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

        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) {
            NowShowingItem()
        }
    }
}

private fun getDummyMovies(): List<Movie> {
    return listOf(
        Movie("Name 001", "3.2"),
        Movie("Name 002", "3.2"),
        Movie("Name 003", "3.2"),
        Movie("Name 004", "3.2"),
        Movie("Name 005", "3.2")
    )
}


@Composable
@Preview
private fun NowShowingItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(MarginThreeHundred)
            .padding(MarginFifteen),
        elevation = CardDefaults.cardElevation(defaultElevation = MarginFive),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(MarginFifteen)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Image(
                painter = painterResource(id = R.drawable.ic_movie_bg),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "MovieBG",
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Movie Name",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x80000000))
                    .padding(MarginFifteen),
                style = Twenty400White
            )
        }
    }
}