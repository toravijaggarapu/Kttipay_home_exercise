package com.kttipay.sample.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.kttipay.sample.R
import com.kttipay.sample.screens.router.MovieDBNavigation
import com.kttipay.sample.ui.theme.KttipayTextStyle
import com.kttipay.sample.ui.theme.LightGreen
import com.kttipay.sample.ui.theme.MarginTwenty
import com.kttipay.sample.ui.theme.SplashBgBlack
import com.kttipay.sample.ui.theme.SplashDarkGreen
import com.kttipay.sample.ui.theme.SplashDkBlackGreen
import com.kttipay.sample.ui.theme.SplashLightGreen
import kotlinx.coroutines.delay

private const val SPLASH_DELAY = 3000L

@Composable
fun SplashScreen(
    navController: NavController = NavController(LocalContext.current)
) {
    SplashView()
    LaunchedEffect(Unit) {
        delay(SPLASH_DELAY)
        navController.navigate(MovieDBNavigation.MovieListScreen().destination) {
            popUpTo(MovieDBNavigation.MovieDBRoute().route) {
                inclusive = true
            }
        }
    }
}

@Composable
@Preview
private fun SplashView() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Surface(color = Color.White) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                SplashBgBlack,
                                SplashBgBlack,
                                SplashBgBlack,
                                SplashBgBlack,
                                SplashBgBlack,
                                SplashBgBlack,
                                SplashBgBlack,
                                SplashDkBlackGreen,
                                SplashDarkGreen,
                                SplashLightGreen
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.the_movie_db),
                    style = KttipayTextStyle.ThreeSix700Green
                )

                CommonProgress(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(MarginTwenty)
                )
            }
        }
    }
}

@Composable
@Preview
fun CommonProgress(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = LightGreen,
        trackColor = SplashDkBlackGreen
    )
}