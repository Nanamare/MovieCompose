package com.nanamare.movie.ui.paging.indb

import androidx.paging.*
import com.nanamare.data.model.MovieDto
import com.nanamare.movie.model.Movie
import com.nanamare.movie.model.mapper.toVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TrendingPagingUseCase @Inject constructor(
    private val trendingMovieRemoteMediator: TrendingMovieRemoteMediator
) {
    operator fun invoke(): Flow<PagingData<Movie>> = Pager(
        PagingConfig(pageSize = 20),
        pagingSourceFactory = { trendingMovieRemoteMediator.getAllMovies() },
        remoteMediator = trendingMovieRemoteMediator
    ).flow.map {
        it.map(MovieDto::toVo)
    }
}