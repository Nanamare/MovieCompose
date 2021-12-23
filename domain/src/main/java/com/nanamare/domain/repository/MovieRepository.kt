package com.nanamare.domain.repository

import com.nanamare.domain.model.GenreListModel
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.usecase.DiscoverQuery
import com.nanamare.domain.usecase.SearchQuery

interface MovieRepository {
    suspend fun getUpcomingMovie(page: Int): MovieModel
    suspend fun searchMovie(searchQuery: SearchQuery): MovieModel
    suspend fun getGenreList(): GenreListModel
    suspend fun getGenreMovies(discoverQuery: DiscoverQuery): MovieModel
    suspend fun getTrendingMovie(page: Int): MovieModel
}