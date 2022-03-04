package com.nanamare.domain.usecase

import com.nanamare.domain.di.IoDispatcher
import com.nanamare.domain.model.MovieResponseModel
import com.nanamare.domain.repository.MovieRemoteRepository
import com.nanamare.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetTrendingMovieUseCase @Inject constructor(
    private val movieRepository: MovieRemoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Int, MovieResponseModel>(dispatcher) {

    override suspend fun execute(param: Int) = movieRepository.getTrendingMovie(param)

}