package com.nanamare.movie.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nanamare.base.util.NetworkConnection
import com.nanamare.base.util.UiState
import com.nanamare.base.util.toUiState
import com.nanamare.domain.model.GenreModel
import com.nanamare.domain.usecase.GetGenreListUseCase
import com.nanamare.movie.model.Movie
import com.nanamare.movie.ui.base.NavigationViewModel
import com.nanamare.movie.ui.base.NavigationViewModelImpl
import com.nanamare.movie.ui.paging.indb.TrendingPagingManager
import com.nanamare.movie.ui.paging.inmemory.SearchMoviePagingSource
import com.nanamare.movie.ui.paging.inmemory.UpcomingMoviePagingSource
import com.nanamare.movie.ui.screen.Mode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

interface MainActivityViewModel : NavigationViewModel {
    val currentMode: StateFlow<Mode>
    val upcomingMovie: Flow<PagingData<Movie>>
    val trendingMovie: Flow<PagingData<Movie>>
    val searchMovie: StateFlow<PagingData<Movie>>
    val genreListUiState: StateFlow<UiState<List<GenreModel>>>
    val isRefresh: StateFlow<Boolean>
    val keyboardTrigger: SharedFlow<Long>
    val error: SharedFlow<Unit>
    val queryFlow: StateFlow<String>

    fun setQuery(query: String)
    fun refresh(refresh: Boolean)
    fun changeMode(mode: Mode)
}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainActivityViewModelImpl @Inject constructor(
    trendingPagingManager: TrendingPagingManager,
    private val searchMoviePagingSource: SearchMoviePagingSource,
    private val getGenreListUseCase: GetGenreListUseCase,
    private val upcomingMoviePagingSource: Provider<UpcomingMoviePagingSource>,
    private val networkConnection: NetworkConnection
) : NavigationViewModelImpl(), MainActivityViewModel, NetworkConnection by networkConnection {

    private val _error = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val error = _error.asSharedFlow()

    private val _queryFlow = MutableStateFlow("")
    override val queryFlow = _queryFlow.asStateFlow()

    private val _currentMode = MutableStateFlow(Mode.NORMAL)
    override val currentMode: StateFlow<Mode> = _currentMode

    private val _keyboardTrigger = MutableSharedFlow<Long>()
    override val keyboardTrigger: SharedFlow<Long> = _keyboardTrigger

    override fun setQuery(query: String) {
        if (!isConnected()) {
            _error.tryEmit(Unit)
            return
        }
        _queryFlow.tryEmit(query)
    }

    override val searchMovie = _queryFlow
        .filter(String::isNotEmpty)
        .debounce(TimeUnit.MILLISECONDS.toMillis(750))
        .flatMapLatest { query ->
            _keyboardTrigger.emit(System.currentTimeMillis())
            Pager(PagingConfig(pageSize = 10)) {
                searchMoviePagingSource.setQuery(query)
            }.flow
                .cachedIn(viewModelScope)
                .catch { Timber.e(it) }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLS),
            PagingData.empty()
        )

    override fun changeMode(mode: Mode) {
        _currentMode.tryEmit(mode)
    }

    override fun refresh(refresh: Boolean) {
        viewModelScope.launch {
            _isRefresh.emit(refresh)
        }
    }

    private val _isRefresh = MutableStateFlow(false)
    override val isRefresh: StateFlow<Boolean> = _isRefresh

    override val upcomingMovie =
        Pager(PagingConfig(pageSize = 10), pagingSourceFactory = upcomingMoviePagingSource::get)
            .flow
            .cachedIn(viewModelScope)
            .catch { Timber.e(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLS),
                PagingData.empty()
            )

    override val genreListUiState = flow {
        emit(getGenreListUseCase().toUiState())
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLS),
        UiState.Loading
    )

    override val trendingMovie = trendingPagingManager()
        .cachedIn(viewModelScope)
        .catch { Timber.e(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLS),
            PagingData.empty()
        )

    companion object {
        private const val STOP_TIME_OUT_MILLS = 5000L
    }
}
