package com.nanamare.domain.repository

import com.nanamare.domain.model.GenreListModel
import com.nanamare.domain.model.MovieResponseModel
import com.nanamare.domain.usecase.DiscoverQuery
import com.nanamare.domain.usecase.SearchQuery

interface MovieRemoteRepository {
    suspend fun getUpcomingMovie(page: Int): MovieResponseModel
    suspend fun searchMovie(searchQuery: SearchQuery): MovieResponseModel
    suspend fun getGenreList(): GenreListModel
    suspend fun getGenreMovies(discoverQuery: DiscoverQuery): MovieResponseModel
    suspend fun getTrendingMovie(page: Int): MovieResponseModel
}