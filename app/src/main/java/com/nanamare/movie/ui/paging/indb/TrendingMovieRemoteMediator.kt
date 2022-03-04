package com.nanamare.movie.ui.paging.indb

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nanamare.data.model.MovieDto
import com.nanamare.data.model.MovieRemoteKeyDto
import com.nanamare.data.model.mapper.toDto
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.usecase.GetTrendingMovieUseCase
import com.nanamare.movie.db.MovieDatabase
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TrendingMovieRemoteMediator @Inject constructor(
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase,
    private val movieDatabase: MovieDatabase,
) : RemoteMediator<Int, MovieDto>() {

    private val movieDao = movieDatabase.getMovieDao()
    private val movieRemoteKeyDao = movieDatabase.getMovieRemoteKeyDao()

    private val cacheTimeOut by lazy { TimeUnit.MILLISECONDS.convert(7, TimeUnit.DAYS) }

    override suspend fun initialize(): InitializeAction =
        if (System.currentTimeMillis() - getLastUpdatedTime() >= cacheTimeOut) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }

    private suspend fun getLastUpdatedTime() =
        movieRemoteKeyDao.getLatestMovieRemoteKey()?.lastUpdated ?: System.currentTimeMillis()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDto>
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
                    movieDao.deleteAll()
                    movieRemoteKeyDao.deleteAll()
                }
                val remotePage = response.page
                val prevPage = if (remotePage == 1) null else remotePage - 1
                val nextPage = if (remotePage > response.totalPages) null else remotePage.plus(1)

                val remoteKey = response.movies.map { movie ->
                    MovieRemoteKeyDto(
                        id = movie.id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                }

                movieRemoteKeyDao.addRemoteKey(remoteKey)
                movieDao.addMovies(response.movies.map(MovieModel::toDto))
            }

            return MediatorResult.Success(endOfPaginationReached = response.movies.isEmpty())
        } catch (e: Exception) {
            Timber.e(e)
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieDto>,
    ): MovieRemoteKeyDto? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.id?.let { id ->
            movieDatabase.withTransaction {
                movieRemoteKeyDao.getRemoteKey(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieDto>,
    ): MovieRemoteKeyDto? =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.id?.let { id ->
            movieDatabase.withTransaction {
                movieRemoteKeyDao.getRemoteKey(id)
            }
        }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieDto>,
    ): MovieRemoteKeyDto? =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id?.let { id ->
            movieDatabase.withTransaction {
                movieRemoteKeyDao.getRemoteKey(id)
            }
        }

}