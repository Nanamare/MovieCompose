package com.nanamare.movie.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nanamare.base.ui.compose.SimpleTopBar
import com.nanamare.domain.model.GenreModel
import com.nanamare.movie.R
import com.nanamare.movie.ui.MainActivityViewModel
import com.nanamare.movie.ui.NavigationViewModel
import com.nanamare.movie.ui.NavigationViewModelImpl
import com.nanamare.movie.ui.mainActivityViewModel

@Composable
fun GenreScreen(
    modifier: Modifier,
    viewModel: MainActivityViewModel = mainActivityViewModel()
) {
    Scaffold(topBar = { SimpleTopBar { Text(text = stringResource(R.string.genre)) } }) { innerPadding ->
        val genreList by viewModel.genreList.collectAsState()
        GenreList(modifier = modifier.padding(innerPadding), genreList, block = viewModel::navigate)
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
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .clickable { block(NavigationViewModel.Screen.GenreDetail(genre)) }
                        .fillMaxWidth()
                        .padding(20.dp),
                    text = genre.name
                )
            }
        }
    }
}

