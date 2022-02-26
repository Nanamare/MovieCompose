package com.nanamare.movie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.nanamare.movie.BuildConfig
import com.nanamare.movie.model.Result
import com.nanamare.movie.ui.MainActivityViewModel
import com.nanamare.movie.ui.NavigationViewModel
import com.nanamare.movie.ui.getViewModel

@Composable
fun TrendingScreen(
    modifier: Modifier,
    viewModel: MainActivityViewModel = getViewModel()
) {
    val trendingMovie = viewModel.trendingMovie.collectAsLazyPagingItems()
    TrendingList(modifier, trendingMovie, block = viewModel::navigate)
}

@Composable
fun TrendingList(
    modifier: Modifier,
    movies: LazyPagingItems<Result>,
    block: (NavigationViewModel.Screen) -> Unit
) {
    LazyColumn(modifier) {
        // Result' id is not stable
        items(movies /*, key = ResultModel::id */) { movie ->
            if (movie == null) return@items
            TrendingCard(movie, block)
        }
    }
}

@Composable
fun TrendingCard(movie: Result, block: (NavigationViewModel.Screen) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { block(NavigationViewModel.Screen.DetailMovie(movie)) }
            .fillMaxWidth()
            .height(200.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovieThumbnail(movie.posterPath, movie.popularity, movie.adult)
        MovieInfo(movie)
    }
}

@Composable
private fun MovieThumbnail(posterPath: String, popularity: Double, adult: Boolean) {
    Box {
        Image(
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxHeight(),
            painter = rememberImagePainter("${BuildConfig.TMDB_IMAGE_URL}${posterPath}") {
            },
            contentDescription = "MovieThumbnail"
        )
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .fillMaxHeight()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .border(0.5.dp, Color.Yellow, shape = RoundedCornerShape(4.dp))
                    .padding(vertical = 2.dp, horizontal = 4.dp),
                text = "Popularity $popularity",
                color = Color.Yellow,
                fontSize = 9.sp
            )
        }
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .border(
                        width = 0.5.dp,
                        color = if (adult) {
                            Color.Red
                        } else {
                            Color.Green
                        }, shape = RoundedCornerShape(4.dp)
                    )
                    .padding(vertical = 2.dp, horizontal = 4.dp),
                text = if (adult) {
                    "19"
                } else {
                    "ALL"
                },
                color = if (adult) {
                    Color.Red
                } else {
                    Color.Green
                },
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun MovieInfo(movie: Result) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row {
            Text(text = "Title : ", style = titleStyle)
            Text(
                text = movie.title,
                style = contentStyle.copy(textDecoration = TextDecoration.Underline)
            )
        }
        Row {
            Text(text = "Release Date : ", style = titleStyle)
            Text(text = movie.releaseDate.toString(), style = contentStyle)
        }
        Row {
            Text(text = "Vote Count : ", style = titleStyle)
            Text(text = movie.voteCount.toString(), style = contentStyle)
        }
        Row {
            Text(text = "Vote Average : ", style = titleStyle)
            Text(text = "${movie.voteAverage}", style = contentStyle.copy(color = Color.Blue))
            Text(text = "/ 10", style = contentStyle)
        }
        Column {
            Text(text = "Overview", style = titleStyle)
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = movie.overview,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                style = contentStyle
            )
        }
    }
}

val titleStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp)
val contentStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp)
