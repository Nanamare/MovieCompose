package com.nanamare.domain.usecase

import com.nanamare.domain.di.IoDispatcher
import com.nanamare.domain.model.GenreModel
import com.nanamare.domain.repository.MovieRemoteRepository
import com.nanamare.domain.usecase.base.NonParamCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
    private val movieRepository: MovieRemoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NonParamCoroutineUseCase<List<GenreModel>>(dispatcher) {

    override suspend fun execute(): List<GenreModel> = movieRepository.getGenreList().genres
}