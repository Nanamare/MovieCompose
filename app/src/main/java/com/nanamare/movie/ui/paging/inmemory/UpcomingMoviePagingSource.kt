package com.nanamare.movie.ui.paging.inmemory

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.usecase.GetUpcomingMovieUseCase
import com.nanamare.movie.model.Movie
import com.nanamare.movie.model.mapper.toVo
import timber.log.Timber
import javax.inject.Inject

class UpcomingMoviePagingSource @Inject constructor(
    private val getUpcomingMovieUseCase: GetUpcomingMovieUseCase
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = KEY_START

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: KEY_START
            val response = getUpcomingMovieUseCase.invoke(nextPage).getOrThrow()
            LoadResult.Page(
                data = response.movies
                    .filter { !it.posterPath.isNullOrEmpty() }
                    .map(MovieModel::toVo),
                prevKey = if (response.page == 1) null else nextPage - 1,
                nextKey = if (nextPage > response.totalPages) null else response.page.plus(1)
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val KEY_START = 1
    }
}