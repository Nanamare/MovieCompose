package com.nanamare.data.remote.impl

import com.nanamare.data.model.mapper.toDomainModel
import com.nanamare.data.remote.MovieApi
import com.nanamare.domain.model.GenreListModel
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.repository.MovieRepository
import com.nanamare.domain.usecase.DiscoverQuery
import com.nanamare.domain.usecase.SearchQuery
import retrofit2.await
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieApi: MovieApi) : MovieRepository {

    override suspend fun getUpcomingMovie(page: Int): MovieModel =
        movieApi.getUpcomingMovie(page).await().toDomainModel()

    override suspend fun searchMovie(searchQuery: SearchQuery): MovieModel =
        movieApi.searchMovie(searchQuery.page, searchQuery.query).await().toDomainModel()

    override suspend fun getGenreList(): GenreListModel =
        movieApi.getGenreList().await().toDomainModel()

    override suspend fun getGenreMovies(discoverQuery: DiscoverQuery): MovieModel =
        movieApi.getGenreMovies(discoverQuery.page, discoverQuery.genre.value).await()
            .toDomainModel()

    override suspend fun getTrendingMovie(page: Int): MovieModel =
        movieApi.getTrendingMovie(page = page).await().toDomainModel()
}