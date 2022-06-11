package com.nanamare.movie.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nanamare.base.ui.compose.ErrorItem
import com.nanamare.base.ui.compose.LoadingView
import com.nanamare.base.ui.compose.SimpleTopBar
import com.nanamare.base.util.UiState
import com.nanamare.domain.model.GenreModel
import com.nanamare.movie.R
import com.nanamare.movie.ui.MainActivityViewModel
import com.nanamare.movie.ui.base.NavigationViewModel
import com.nanamare.movie.ui.base.getActivityViewModel

@Composable
fun GenreScreen(
    modifier: Modifier,
    viewModel: MainActivityViewModel = getActivityViewModel()
) {
    Scaffold(topBar = { SimpleTopBar { Text(text = stringResource(R.string.genre)) } }) { innerPadding ->
        val uiState by viewModel.genreListUiState.collectAsState()
        when (val genre = uiState) {
            UiState.Loading -> LoadingView(Modifier.fillMaxSize())
            is UiState.Error -> {
                ErrorItem(
                    message = "Error :(",
                    modifier = Modifier.fillMaxSize(),
                    onClickRetry = { /* Handling retry */ }
                )
            }
            is UiState.Success -> {
                GenreList(
                    modifier = modifier.padding(innerPadding),
                    genreList = genre.data,
                    block = viewModel::navigate
                )
            }
        }
    }
}

@Composable
fun GenreList(
    modifier: Modifier,
    genreList: List<GenreModel>,
    block: (NavigationViewModel.Screen) -> Unit
) {
    LazyColumn(modifier.padding(horizontal = 12.dp)) {
        items(genreList, key = { genre -> genre.id }) { genre ->
            MovieOutlineButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { block(NavigationViewModel.Screen.GenreDetail(genre)) }
            ) {
                Text(text = genre.name)
            }
        }
    }
}

@Composable
private fun MovieOutlineButton(
    modifier: Modifier,
    onClick: () -> Unit,
    content: (@Composable () -> Unit)? = null,
) {
    OutlinedButton(
        modifier = modifier.height(45.dp),
        onClick = onClick,
        border = ButtonDefaults.outlinedBorder.copy(brush = SolidColor(MaterialTheme.colors.primary))
    ) {
        content?.let {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                val textStyle = MaterialTheme.typography.subtitle1
                ProvideTextStyle(textStyle, content)
            }
        }
    }
}

