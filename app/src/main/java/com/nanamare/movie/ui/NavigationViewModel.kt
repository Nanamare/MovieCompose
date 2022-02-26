package com.nanamare.movie.ui

import com.nanamare.domain.model.GenreModel
import com.nanamare.movie.model.Result
import kotlinx.coroutines.flow.Flow

interface NavigationViewModel: BaseViewModel {
    fun navigate(screen: Screen)
    val navigation: Flow<Screen>

    sealed class Screen {
        data class DetailMovie(val result: Result) : Screen()
        data class GenreDetail(val genreModel: GenreModel) : Screen()
    }
}