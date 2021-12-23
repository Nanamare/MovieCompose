package com.nanamare.domain.usecase

import com.nanamare.domain.di.IoDispatcher
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.repository.MovieRepository
import com.nanamare.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetTrendingMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Int, MovieModel>(dispatcher) {

    override suspend fun execute(param: Int) = movieRepository.getTrendingMovie(param)

}