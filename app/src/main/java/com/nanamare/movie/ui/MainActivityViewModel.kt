package com.nanamare.movie.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.paging.PagingData
import com.nanamare.domain.model.GenreModel
import com.nanamare.movie.model.Result
import com.nanamare.movie.ui.screen.Mode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MainActivityViewModel : NavigationViewModel {

    fun setQuery(query: String)
    val currentMode: StateFlow<Mode>
    fun changeMode(mode: Mode)
    val upcomingMovie: Flow<PagingData<Result>>
    val trendingMovie: Flow<PagingData<Result>>
    val searchMovie: StateFlow<PagingData<Result>>
    val genreList: StateFlow<List<GenreModel>>
    fun refreshMovie()
    val isRefresh: StateFlow<Boolean>
    val keyboardTrigger: SharedFlow<Long>
    var searchQuery: String

}

private val LoginViewModelFactory = compositionLocalOf<@Composable () -> MainActivityViewModel> {
    { error("Not viewModel provided") }
}

fun provideLoginViewModelFactory(viewModelFactory: @Composable () -> MainActivityViewModel) =
    LoginViewModelFactory provides viewModelFactory

@Composable
fun mainActivityViewModel() = LoginViewModelFactory.current()