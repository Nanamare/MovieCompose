package com.nanamare.movie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nanamare.base.ui.compose.ErrorItem
import com.nanamare.base.ui.compose.LoadingView
import com.nanamare.base.ui.compose.throttleSingleClick
import com.nanamare.base.util.UiState
import com.nanamare.data.BuildConfig
import com.nanamare.domain.model.MovieImagesModel
import com.nanamare.movie.model.Movie
import com.nanamare.movie.ui.detail.DetailMovieViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailMovieScreen(movie: Movie, viewModel: DetailMovieViewModel) {

    val movieImagesUiState by viewModel.movieImagesUiState.collectAsState()

    when (val uiState = movieImagesUiState) {
        UiState.Loading -> LoadingView(Modifier.fillMaxSize())

        is UiState.Error -> {
            ErrorItem(
                message = "Error :(",
                modifier = Modifier.fillMaxSize(),
                onClickRetry = { /* Handling retry */ }
            )
        }

        is UiState.Success -> {
            DetailMovieSection(uiState.data)
        }
    }
}

private const val IndicatorMaxSize = 8

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun DetailMovieSection(movieImageModel: MovieImagesModel) {

    var currentPage by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    val maxCount by remember { mutableStateOf(minOf(movieImageModel.posters.size, IndicatorMaxSize)) }

    Box {
        val pagerState = rememberPagerState()
        HorizontalPager(
            count = maxCount,
            state = pagerState,
            modifier = Modifier
                .pointerInput(Unit) {
                    interceptOutOfBoundsChildEvents = true
                }
                .fillMaxSize(),
        ) { page: Int ->
            ZoomableBox(enableRotation = false) {
                AsyncImage(
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offsetX,
                            translationY = offsetY,
                            rotationZ = rotationZ
                        ),
                    model = "${BuildConfig.TMDB_IMAGE_ORIGINAL_URL}${movieImageModel.posters[page].filePath}",
                    placeholder = rememberAsyncImagePainter(model = LoadingView(modifier = Modifier.fillMaxSize())),
                    contentDescription = "DetailMovieImage"
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
        )

        if (currentPage > 0) {
            Image(
                painter = rememberVectorPainter(image = Icons.Outlined.ArrowBack),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
                    .align(Alignment.BottomStart)
                    .clip(CircleShape)
                    .alpha(0.5f)
                    .background(color = Color.Gray)
                    .throttleSingleClick {
                        scope.launch {
                            currentPage += -1
                            pagerState.scrollToPage(currentPage)
                        }
                    }
            )
        }

        if (currentPage < maxCount - 1) {
            Image(
                painter = rememberVectorPainter(image = Icons.Outlined.ArrowForward),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .alpha(0.5f)
                    .background(color = Color.Gray)
                    .throttleSingleClick {
                        scope.launch {
                            currentPage += 1
                            pagerState.scrollToPage(currentPage)
                        }
                    }
            )
        }

    }
}

@Composable
fun ZoomableBox(
    modifier: Modifier = Modifier,
    enableRotation: Boolean = false,
    minScale: Float = 0.5f,
    maxScale: Float = 3f,
    content: @Composable ZoomableBoxScope.() -> Unit
) {
    var rotationZ by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var size by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .clip(RectangleShape)
            .onSizeChanged { size = it }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    scale = maxOf(minScale, minOf(scale * zoom, maxScale))
                    if (scale > 1f) {
                        val maxX = (size.width * (scale - 1)) / 2
                        val minX = -maxX
                        offsetX = maxOf(minX, minOf(maxX, offsetX + pan.x))
                        val maxY = (size.height * (scale - 1)) / 2
                        val minY = -maxY
                        offsetY = maxOf(minY, minOf(maxY, offsetY + pan.y))
                    }
                    if (enableRotation) {
                        rotationZ += rotation
                    }
                }
            }
    ) {
        ZoomableBoxScopeImpl(scale, offsetX, offsetY, rotationZ).content()
    }
}

interface ZoomableBoxScope {
    val scale: Float
    val offsetX: Float
    val offsetY: Float
    val rotationZ: Float
}

private data class ZoomableBoxScopeImpl(
    override val scale: Float,
    override val offsetX: Float,
    override val offsetY: Float,
    override val rotationZ: Float
) : ZoomableBoxScope