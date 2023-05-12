package com.nanamare.data.source.remote.impl

import com.nanamare.data.model.mapper.toDomainModel
import com.nanamare.data.source.remote.MovieApi
import com.nanamare.domain.model.GenreListModel
import com.nanamare.domain.model.MovieImagesModel
import com.nanamare.domain.model.MovieResponseModel
import com.nanamare.domain.repository.MovieRemoteRepository
import com.nanamare.domain.usecase.DiscoverQuery
import com.nanamare.domain.usecase.SearchQuery
import retrofit2.await
import javax.inject.Inject

class MovieRemoteRepositoryImpl @Inject constructor(private val movieApi: MovieApi) :
    MovieRemoteRepository {

    override suspend fun getUpcomingMovie(page: Int): MovieResponseModel =
        movieApi.getUpcomingMovie(page).await().toDomainModel()

    override suspend fun searchMovie(searchQuery: SearchQuery): MovieResponseModel =
        movieApi.searchMovie(searchQuery.page, searchQuery.query).await().toDomainModel()

    override suspend fun getGenreList(): GenreListModel =
        movieApi.getGenreList().await().toDomainModel()

    override suspend fun getGenreMovies(discoverQuery: DiscoverQuery): MovieResponseModel =
        movieApi.getGenreMovies(discoverQuery.page, discoverQuery.genre.value).await()
            .toDomainModel()

    override suspend fun getTrendingMovie(page: Int): MovieResponseModel =
        movieApi.getTrendingMovie(page = page).await().toDomainModel()

    override suspend fun getMovieImages(movieId: String): MovieImagesModel =
        movieApi.getMovieImages(movieId).await().toDomainModel()
}