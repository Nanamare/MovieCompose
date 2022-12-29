package com.nanamare.domain.usecase

import com.nanamare.domain.di.IoDispatcher
import com.nanamare.domain.model.MovieImagesModel
import com.nanamare.domain.repository.MovieRemoteRepository
import com.nanamare.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetMovieImageUseCase @Inject constructor(
    private val movieRepository: MovieRemoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, MovieImagesModel>(dispatcher) {

    override suspend fun execute(param: String): MovieImagesModel = movieRepository.getMovieImages(param)

}