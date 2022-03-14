package com.nanamare.movie.ui.base

import com.nanamare.domain.model.GenreModel
import com.nanamare.movie.model.Movie
import kotlinx.coroutines.flow.Flow

interface NavigationViewModel : BaseViewModel {
    fun navigate(screen: Screen)
    val navigation: Flow<Screen>

    sealed class Screen {
        data class DetailMovie(val movie: Movie) : Screen()
        data class GenreDetail(val genreModel: GenreModel) : Screen()
    }
}