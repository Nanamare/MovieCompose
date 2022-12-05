package com.nanamare.movie.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nanamare.base.ui.compose.*
import com.nanamare.base.util.rememberFlowWithLifecycle
import com.nanamare.base.util.toast
import com.nanamare.data.BuildConfig
import com.nanamare.movie.R
import com.nanamare.movie.model.Movie
import com.nanamare.movie.ui.MainActivityViewModel
import com.nanamare.movie.ui.base.NavigationViewModel
import com.nanamare.movie.ui.base.getActivityViewModel
import com.nanamare.movie.ui.screen.Mode.NORMAL
import com.nanamare.movie.ui.screen.Mode.SEARCH

@Composable
fun UpcomingScreen(
    modifier: Modifier,
    viewModel: MainActivityViewModel = getActivityViewModel()
) {
    val context = LocalContext.current

    val error = rememberFlowWithLifecycle(viewModel.error)
    LaunchedEffect(error) {
        error.collect { context.toast("network is lost") }
    }

    val currentType by viewModel.currentMode.collectAsState()

    if (currentType == SEARCH) {
        BackHandler {
            viewModel.changeMode(NORMAL)
        }
    }

    Scaffold(
        topBar = {
            SearchAppBar(
                title = stringResource(R.string.toolbar_title_search),
                currentType = currentType,
                onSymbolClick = {
                    // TODO Change Theme
                },
                onChangeType = viewModel::changeMode
            ) { query ->
                viewModel.setQuery(query)
            }
        }) { innerPadding ->
        when (currentType) {
            NORMAL -> {
                val movies = viewModel.upcomingMovie.collectAsLazyPagingItems()
                val refresh by viewModel.isRefresh.collectAsState()

                SwipeRefresh(
                    state = rememberSwipeRefreshState(refresh),
                    onRefresh = { viewModel.refresh(true) }
                ) {
                    UpcomingMovieList(
                        modifier = modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        movies = movies,
                        isRefresh = refresh,
                        loadingFinish = { viewModel.refresh(false) },
                        block = viewModel::navigate
                    )
                }
            }

            SEARCH -> {
                val searchMovie = viewModel.searchMovie.collectAsLazyPagingItems()
                val keyboardTrigger by viewModel.keyboardTrigger.collectAsState(0L)
                SearchMovieList(
                    modifier = modifier.padding(innerPadding),
                    movies = searchMovie,
                    isNotEmptyQuery = { viewModel.queryFlow.value.isNotEmpty() },
                    keyboardTrigger = keyboardTrigger,
                    block = viewModel::navigate
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UpcomingMovieList(
    modifier: Modifier,
    movies: LazyPagingItems<Movie>,
    isRefresh: Boolean,
    loadingFinish: () -> Unit,
    block: (NavigationViewModel.Screen) -> Unit
) {

    movies.apply {

        if (isRefresh) {
            refresh()
        }

        LazyVerticalGrid(
            columns = Fixed(2),
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(movies) { movie, position ->
                MovieThumbnail(movie, position, block)
            }

            when {
                loadState.refresh is LoadState.NotLoading && loadState.prepend.endOfPaginationReached -> {
                    loadingFinish()
                }

                loadState.refresh is LoadState.Loading || isRefresh -> {
                    item(span = { GridItemSpan(2) }) {
                        LoadingView(Modifier.fillMaxSize())
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = movies.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = error.error.localizedMessage.orEmpty(),
                            modifier = Modifier.fillMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = movies.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = error.error.localizedMessage.orEmpty(),
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchMovieList(
    modifier: Modifier,
    movies: LazyPagingItems<Movie>,
    isNotEmptyQuery: () -> Boolean,
    keyboardTrigger: Long,
    block: (NavigationViewModel.Screen) -> Unit
) {
    val scaffoldState = LocalScaffoldState.current

    LazyColumn(modifier = modifier) {
        items(movies, Movie::id) { movie ->
            if (movie == null) return@items
            MovieCard(movie, block)
        }
        setPagingStateListener(
            movies,
            isNotEmptyQuery = isNotEmptyQuery,
            refresh = { item { LoadingView(Modifier.fillParentMaxSize()) } },
            append = { item { LoadingItem() } },
            empty = {
                item {
                    EmptySnackBar(scaffoldState)
                    EmptyPlaceHolder(Modifier.fillParentMaxSize())
                }
            }
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(keyboardTrigger) {
        if (keyboardTrigger == 0L) return@LaunchedEffect
        keyboardController?.hide()
        focusManager.clearFocus()
    }
}

@Composable
private fun MovieCard(movie: Movie, block: (NavigationViewModel.Screen) -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val imageRatio = screenWidth / screenHeight

    SubcomposeAsyncImage(
        modifier = Modifier
            .aspectRatio(imageRatio)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = { block(NavigationViewModel.Screen.DetailMovie(movie)) }
            ),
        model = "${BuildConfig.TMDB_IMAGE_URL}${movie.posterPath}",
        loading = {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(52.dp))
            }
        },
        contentDescription = "MovieThumbnail",
        contentScale = ContentScale.FillHeight,
        alignment = Alignment.Center
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchAppBar(
    title: String,
    currentType: Mode,
    onSymbolClick: () -> Unit,
    onChangeType: (Mode) -> Unit,
    block: (searchQuery: String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    // Move to another screen and when you return restore it
    var searchText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    // when restore searchText, move cursor to text.length
    searchText = if (currentType == NORMAL) {
        TextFieldValue()
    } else {
        block(searchText.text)
        TextFieldValue(searchText.text, TextRange(searchText.text.length))
    }

    SimpleTopBar(
        navigationIcon = {
            IconButton(onClick = onSymbolClick) {
                Icon(painter = painterResource(id = R.drawable.ic_sketch_symbol), null)
            }
        },
        actions = {
            if (currentType == SEARCH) {
                IconButton(
                    onClick = { onChangeType(NORMAL) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "CloseButton",
                        tint = Color.White
                    )
                }
            } else {
                keyboardController?.hide()
                IconButton(
                    onClick = { onChangeType(SEARCH) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "SearchButton",
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        if (currentType == SEARCH) {
            Surface {
                TextField(
                    textStyle = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary),
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        block(searchText.text)
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search),
                            color = Color.White
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            block(searchText.text)
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        },
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.White,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }
            SideEffect {
                if (searchText.text.isEmpty()) {
                    focusRequester.requestFocus()
                }
            }
        } else {
            Text(text = title)
        }
    }
}

@Composable
private fun MovieThumbnail(
    movie: Movie,
    index: Int,
    block: (NavigationViewModel.Screen) -> Unit
) {
    val bottomPadding = if (index % 2 == 1) 8.dp else 0.dp
    val moviePosterRatio = 1f / 1.3f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bottomPadding),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .aspectRatio(moviePosterRatio)
                .fillMaxWidth()
                .align(Alignment.Center)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = { block(NavigationViewModel.Screen.DetailMovie(movie)) }
                ),
            model = "${BuildConfig.TMDB_IMAGE_URL}${movie.posterPath}",
            placeholder = rememberAsyncImagePainter(
                model = Image(
                    painter = painterResource(id = R.drawable.outline_movie_24),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            ),
            contentDescription = "MovieThumbnail",
            alignment = Alignment.Center
        )
    }
}

enum class Mode {
    NORMAL, SEARCH
}


