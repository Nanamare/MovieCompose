package com.nanamare.movie.ui.genre

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.savedstate.SavedStateRegistryOwner
import com.nanamare.movie.model.Genre
import com.nanamare.movie.model.Movie
import com.nanamare.movie.ui.base.NavigationViewModel
import com.nanamare.movie.ui.base.NavigationViewModelImpl
import com.nanamare.movie.ui.genre.GenreDetailActivity.Companion.EXTRA_GENRE_KEY
import com.nanamare.movie.ui.paging.inmemory.GenreMoviePagingSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

interface GenreDetailViewModel : NavigationViewModel {
    val genreMovie: StateFlow<PagingData<Movie>>
    val selectedGenre: Genre

    @AssistedFactory
    interface Factory {
        fun create(handle: SavedStateHandle): GenreDetailViewModelImpl
    }
}

class GenreDetailViewModelImpl @AssistedInject constructor(
    private val genreMoviePagingSource: GenreMoviePagingSource,
    @Assisted savedStateHandle: SavedStateHandle
) : NavigationViewModelImpl(), GenreDetailViewModel {

    override val selectedGenre: Genre =
        checkNotNull(savedStateHandle.get<Genre>(EXTRA_GENRE_KEY)).also { Timber.d("genre $it") }

    override val genreMovie: StateFlow<PagingData<Movie>> =
        Pager(PagingConfig(pageSize = 10), pagingSourceFactory = {
            genreMoviePagingSource.apply {
                genre = selectedGenre.id
            }
        }).flow
            .cachedIn(viewModelScope)
            .catch { Timber.e(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

}

class GenreDetailViewModelFactory(
    private val factory: GenreDetailViewModel.Factory,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = factory.create(handle) as T
}
