package com.nanamare.movie.ui.paging.indb

import androidx.paging.*
import com.nanamare.data.local.db.TrendingMovieLimitOffsetPagingSource
import com.nanamare.domain.model.MovieModel
import com.nanamare.movie.model.Movie
import com.nanamare.movie.model.mapper.toVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Provider

@OptIn(ExperimentalPagingApi::class)
class TrendingPagingManager @Inject constructor(
    private val trendingMovieRemoteMediator: TrendingMovieRemoteMediator,
    private val trendingMovieLocalPagingSource: Provider<TrendingMovieLimitOffsetPagingSource>
) {
    operator fun invoke(): Flow<PagingData<Movie>> = Pager(
        PagingConfig(pageSize = 20),
        pagingSourceFactory = { trendingMovieLocalPagingSource.get() },
        remoteMediator = trendingMovieRemoteMediator
    ).flow.map {
        it.map(MovieModel::toVo)
    }
}