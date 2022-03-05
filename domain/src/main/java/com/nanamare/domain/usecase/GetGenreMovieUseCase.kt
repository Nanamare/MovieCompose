package com.nanamare.domain.usecase

import com.nanamare.domain.di.IoDispatcher
import com.nanamare.domain.model.MovieResponseModel
import com.nanamare.domain.repository.MovieRemoteRepository
import com.nanamare.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetGenreMovieUseCase @Inject constructor(
    private val movieRepository: MovieRemoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<DiscoverQuery, MovieResponseModel>(dispatcher) {

    override suspend fun execute(param: DiscoverQuery) = movieRepository.getGenreMovies(param)

}

@JvmInline
value class Genre(val value: String)

data class DiscoverQuery(val genre: Genre, val page: Int)