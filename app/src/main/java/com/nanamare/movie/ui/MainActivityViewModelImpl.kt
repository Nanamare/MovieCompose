package com.nanamare.movie.ui

import androidx.core.util.Supplier
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.nanamare.domain.model.GenreModel
import com.nanamare.domain.usecase.GetGenreListUseCase
import com.nanamare.movie.model.Movie
import com.nanamare.movie.ui.paging.indb.TrendingPagingUseCase
import com.nanamare.movie.ui.paging.inmemory.SearchMoviePagingSource
import com.nanamare.movie.ui.paging.inmemory.UpcomingMoviePagingSource
import com.nanamare.movie.ui.screen.Mode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface MainActivityViewModel : NavigationViewModel {
    fun setQuery(query: String)
    val currentMode: StateFlow<Mode>
    fun changeMode(mode: Mode)
    val upcomingMovie: Flow<PagingData<Movie>>
    val trendingMovie: Flow<PagingData<Movie>>
    val searchMovie: StateFlow<PagingData<Movie>>
    val genreList: StateFlow<List<GenreModel>>
    fun refreshMovie()
    val isRefresh: StateFlow<Boolean>
    val keyboardTrigger: SharedFlow<Long>
    var searchQuery: String
}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
@HiltViewModel
class MainActivityViewModelImpl @Inject constructor(
    private val searchMoviePagingSource: SearchMoviePagingSource,
    trendingPagingUseCase: TrendingPagingUseCase,
    private val getGenreListUseCase: GetGenreListUseCase,
    private val upcomingMoviePagingSource: UpcomingMoviePagingSource
) : NavigationViewModelImpl(), MainActivityViewModel {

    private val searchStartTrigger = MutableSharedFlow<Unit>()

    override var searchQuery = ""

    private val _currentMode = MutableStateFlow(Mode.NORMAL)
    override val currentMode: StateFlow<Mode> = _currentMode

    private val _keyboardTrigger = MutableSharedFlow<Long>()
    override val keyboardTrigger: SharedFlow<Long> = _keyboardTrigger

    override fun setQuery(query: String) {
        Timber.d(query)
        this.searchQuery = query
        viewModelScope.launch {
            searchStartTrigger.emit(Unit)
        }
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
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    override fun changeMode(mode: Mode) {
        _currentMode.tryEmit(mode)
    }

    private val upComingTrigger = flow {
        emit(Unit)
        emitAll(refreshFlow)
    }

    private val refreshFlow = MutableSharedFlow<Unit>()

    override fun refreshMovie() {
        viewModelScope.launch {
            refreshFlow.emit(Unit)
            _isRefresh.emit(true)
        }
    }

    private val _isRefresh = MutableStateFlow(false)
    override val isRefresh: StateFlow<Boolean> = _isRefresh

    override val upcomingMovie = upComingTrigger.flatMapLatest {
        val elapsedTime = System.currentTimeMillis()
        Pager(PagingConfig(pageSize = 10), pagingSourceFactory = ::upcomingMoviePagingSource)
            .flow
            .cachedIn(viewModelScope)
            .catch { Timber.e(it) }
            .map {
                viewModelScope.launch {
                    val fetchTime = System.currentTimeMillis() - elapsedTime
                    if (fetchTime - elapsedTime < 300) {
                        delay(300 - fetchTime)
                        _isRefresh.emit(false)
                    }
                }
                it
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())
    }

    override val genreList = flow {
        emit(getGenreListUseCase.invoke().getOrDefault(emptyList()))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // Don't need cachedIn
    override val trendingMovie = trendingPagingUseCase()
        .catch { Timber.e(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())
}
