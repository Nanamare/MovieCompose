package com.nanamare.movie.ui.paging.indb

import androidx.paging.*
import com.nanamare.data.model.MovieDto
import com.nanamare.domain.usecase.GetTrendingMovieUseCase
import com.nanamare.movie.db.MovieDatabase
import com.nanamare.movie.model.Movie
import com.nanamare.movie.model.mapper.toVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TrendingPagingUseCase @Inject constructor(
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase,
    private val movieDatabase: MovieDatabase,
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        val factory = { movieDatabase.getMovieDao().getAllMovies() }
        return Pager(
            PagingConfig(pageSize = 20),
            pagingSourceFactory = factory,
            remoteMediator = TrendingMovieRemoteMediator(getTrendingMovieUseCase, movieDatabase)
        ).flow.map {
            it.map(MovieDto::toVo)
        }
    }
}