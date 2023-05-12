package com.nanamare.movie.ui.paging.indb

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nanamare.data.source.local.db.MovieDatabase
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.model.MovieRemoteKeyModel
import com.nanamare.domain.repository.MovieLocalRepository
import com.nanamare.domain.usecase.GetTrendingMovieUseCase
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TrendingMovieRemoteMediator @Inject constructor(
    private val movieDatabase: MovieDatabase,
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase, // MovieRemoteRepository
    private val repository: MovieLocalRepository,
) : RemoteMediator<Int, MovieModel>() {

    private val cacheTimeOut by lazy { TimeUnit.MILLISECONDS.convert(7, TimeUnit.DAYS) }

    override suspend fun initialize(): InitializeAction =
        if (System.currentTimeMillis() - repository.getLatestUpdatedTime() >= cacheTimeOut) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieModel>
    ): MediatorResult {
        try {
            val localPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            val response = getTrendingMovieUseCase.invoke(localPage).getOrThrow()

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    repository.deleteAll()
                }
                val remotePage = response.page
                val prevPage = if (remotePage == 1) null else remotePage - 1
                val nextPage = if (remotePage > response.totalPages) null else remotePage.plus(1)

                val remoteKey = response.movies.map { movie ->
                    MovieRemoteKeyModel(
                        id = movie.id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                }

                repository.replaceRemoteKey(remoteKey)
                repository.replaceMovies(response.movies)
            }

            return MediatorResult.Success(endOfPaginationReached = response.movies.isEmpty())
        } catch (e: Exception) {
            Timber.e(e)
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieModel>,
    ): MovieRemoteKeyModel? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.id?.let { id ->
            repository.getRemoteKey(id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieModel>,
    ): MovieRemoteKeyModel? =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.id?.let { id ->
            repository.getRemoteKey(id)
        }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieModel>,
    ): MovieRemoteKeyModel? =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id?.let { id ->
            repository.getRemoteKey(id)
        }

}