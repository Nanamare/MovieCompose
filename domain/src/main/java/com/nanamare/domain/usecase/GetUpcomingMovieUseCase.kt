package com.nanamare.domain.usecase

import com.nanamare.domain.di.IoDispatcher
import com.nanamare.domain.model.MovieResponseModel
import com.nanamare.domain.repository.MovieRemoteRepository
import com.nanamare.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUpcomingMovieUseCase @Inject constructor(
    private val movieRemoteRepository: MovieRemoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Int, MovieResponseModel>(dispatcher) {

    override suspend fun execute(param: Int) = movieRemoteRepository.getUpcomingMovie(page = param)

}