package com.nanamare.movie.ui

import androidx.core.util.Supplier
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nanamare.base.util.NetworkConnection
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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
    val genreList: StateFlow<List<GenreModel>>
    val isRefresh: StateFlow<Boolean>
    val keyboardTrigger: SharedFlow<Long>
    var searchQuery: String
    val error: SharedFlow<Unit>

    fun setQuery(query: String)
    fun pullToRefresh()
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

    private val searchStartTrigger = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override var searchQuery = ""

    private val _currentMode = MutableStateFlow(Mode.NORMAL)
    override val currentMode: StateFlow<Mode> = _currentMode

    private val _keyboardTrigger = MutableSharedFlow<Long>()
    override val keyboardTrigger: SharedFlow<Long> = _keyboardTrigger

    override fun setQuery(query: String) {
        if (!isConnected()) {
            _error.tryEmit(Unit)
            return
        }
        this.searchQuery = query
        searchStartTrigger.tryEmit(Unit)
    }

    override val searchMovie = searchStartTrigger
        .debounce(TimeUnit.MILLISECONDS.toMillis(750))
        .filter { searchQuery.isNotEmpty() }
        .map { _keyboardTrigger.emit(System.currentTimeMillis()) }
        .flatMapLatest {
            Pager(PagingConfig(pageSize = 10)) {
                searchMoviePagingSource.apply {
                    querySupplier = Supplier { searchQuery }
                }
            }.flow
                .cachedIn(viewModelScope)
                .catch { Timber.e(it) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    override fun changeMode(mode: Mode) {
        _currentMode.tryEmit(mode)
    }

    override fun pullToRefresh() {
        viewModelScope.launch {
            _isRefresh.emit(true)
            delay(300)
            _isRefresh.emit(false)
        }
    }

    private val _isRefresh = MutableStateFlow(false)
    override val isRefresh: StateFlow<Boolean> = _isRefresh

    override val upcomingMovie =
        Pager(PagingConfig(pageSize = 10), pagingSourceFactory = upcomingMoviePagingSource::get)
            .flow
            .cachedIn(viewModelScope)
            .catch { Timber.e(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    override val genreList = flow {
        emit(getGenreListUseCase.invoke().getOrDefault(emptyList()))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    override val trendingMovie = trendingPagingManager()
        .cachedIn(viewModelScope)
        .catch { Timber.e(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
}
