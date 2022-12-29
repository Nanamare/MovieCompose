package com.nanamare.movie.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanamare.base.util.UiState
import com.nanamare.base.util.toUiState
import com.nanamare.domain.model.MovieImagesModel
import com.nanamare.domain.usecase.GetMovieImageUseCase
import com.nanamare.movie.ui.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

interface DetailMovieViewModel : BaseViewModel {

    val movieImagesUiState: StateFlow<UiState<MovieImagesModel>>

    @AssistedFactory
    interface Factory {
        fun create(movieId: String): DetailMovieViewModelImpl
    }

}

class DetailMovieViewModelImpl @AssistedInject constructor(
    private val getMovieImageUseCase: GetMovieImageUseCase,
    @Assisted movieId: String
) : DetailMovieViewModel, ViewModel() {

    override val movieImagesUiState = flow {
        emit(getMovieImageUseCase(movieId).toUiState())
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLS),
        UiState.Loading
    )

    companion object {
        private const val STOP_TIME_OUT_MILLS = 5000L
    }

}

