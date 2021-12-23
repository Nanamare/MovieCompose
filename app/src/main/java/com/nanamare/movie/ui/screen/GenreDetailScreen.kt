package com.nanamare.movie.ui.screen

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.nanamare.base.ui.compose.SimpleTopBar
import com.nanamare.base.ui.compose.toPx
import com.nanamare.base.util.rememberFlowWithLifecycle
import com.nanamare.movie.BuildConfig
import com.nanamare.movie.model.Result
import com.nanamare.movie.model.mapper.toVo
import com.nanamare.movie.ui.*
import kotlinx.coroutines.flow.collect
import kotlin.math.ceil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenreDetailScreen(viewModel: GenreDetailViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val genreMovie = viewModel.genreMovie.collectAsLazyPagingItems()
    val itemCount = genreMovie.itemCount

    val title = "Top 40 ${viewModel.selectedGenre.name} Movie"

    Scaffold(topBar = { SimpleTopBar { Text(text = title) } }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            StaggeredVerticalGrid(
                modifier = Modifier.padding(4.dp),
                maxColumnWidth = screenWidth / 2
            ) {
                for (index in 0 until itemCount) {
                    val genre = genreMovie[index] ?: return@StaggeredVerticalGrid
                    GenreCard(genre, block = viewModel::navigate)
                }
            }
        }
    }

    val context = LocalContext.current
    val navigationActions = rememberFlowWithLifecycle(viewModel.navigation)
    LaunchedEffect(navigationActions) {
        navigationActions.collect { screen ->
            when (screen) {
                is NavigationViewModel.Screen.DetailMovie -> {
                    context.startActivity(Intent(context, DetailMovieActivity::class.java))
                }
                is NavigationViewModel.Screen.GenreDetail -> {
                    context.startActivity(
                        Intent(context, GenreDetailActivity::class.java)
                            .putExtra(GenreDetailActivity.EXTRA_GENRE_KEY, screen.genreModel.toVo())
                    )
                }
            }
        }
    }
}

@Composable
fun GenreCard(
    movie: Result,
    modifier: Modifier = Modifier,
    block: (NavigationViewModel.Screen) -> Unit
) {
    Surface(
        modifier = modifier.padding(4.dp),
        color = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = { block(NavigationViewModel.Screen.DetailMovie(movie)) }
                )
                .semantics {
                    contentDescription = "GenreDetailCard"
                }
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(4f / 3f),
                painter = rememberImagePainter(data = "${BuildConfig.TMDB_IMAGE_URL}${movie.posterPath}"),
                contentDescription = "GenreDetailImage"
            )
            Text(
                text = movie.title,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .padding(12.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)

                )
                Text(
                    text = "Summary",
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.overline.copy(fontSize = 12.sp)
                )
                Icon(
                    imageVector = Icons.Rounded.Star,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)

                )
            }
            Text(
                text = movie.overview,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(
                        start = 4.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    ),
                maxLines = 6
            )
        }
    }
}

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measure, constraints ->
        check(constraints.hasBoundedWidth) { "Unbounded width not supported" }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth, minWidth = 200.toPx)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measure.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}