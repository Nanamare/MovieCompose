package com.nanamare.domain.usecase

import com.nanamare.domain.di.IoDispatcher
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.repository.MovieRepository
import com.nanamare.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetGenreMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<DiscoverQuery, MovieModel>(dispatcher) {

    override suspend fun execute(param: DiscoverQuery) = movieRepository.getGenreMovies(param)
    
}

@JvmInline
value class Genre(val value: String)

data class DiscoverQuery(val genre: Genre, val page: Int)