package com.nanamare.domain.usecase

import com.nanamare.domain.di.IoDispatcher
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.repository.MovieRepository
import com.nanamare.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<SearchQuery, MovieModel>(dispatcher) {

    override suspend fun execute(param: SearchQuery) = movieRepository.searchMovie(param)

}

data class SearchQuery(val query: String, val page: Int)