package com.nanamare.movie.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nanamare.domain.model.ResultModel
import com.nanamare.domain.usecase.DiscoverQuery
import com.nanamare.domain.usecase.Genre
import com.nanamare.domain.usecase.GetGenreMovieUseCase
import com.nanamare.movie.model.Result
import com.nanamare.movie.model.mapper.toVo
import timber.log.Timber
import javax.inject.Inject

class GenreMoviePagingSource @Inject constructor(
    private val getGenreMovieUseCase: GetGenreMovieUseCase,
) : PagingSource<Int, Result>() {

    var genre = ""

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? =
        state.anchorPosition?.minus(1)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPage = params.key ?: 1
            val response =
                getGenreMovieUseCase.invoke(DiscoverQuery(Genre(genre), nextPage)).getOrThrow()
            LoadResult.Page(
                data = response.results
                    .filter { !it.posterPath.isNullOrEmpty() }
                    .map(ResultModel::toVo),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage > response.totalPages || nextPage > LIMIT_NEXT_PAGE) {
                    null
                } else {
                    response.page.plus(1)
                }
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    companion object {
        // 1 page = 20 item's
        private const val LIMIT_NEXT_PAGE = 1
    }
}