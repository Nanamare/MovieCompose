package com.nanamare.movie.db

import androidx.paging.PagingSource
import com.nanamare.data.model.MovieDto
import com.nanamare.data.model.MovieRemoteKeyDto
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieRemoteKeyDao: MovieRemoteKeyDao
) : MovieLocalRepository {

    override suspend fun replaceMovies(movieDto: List<MovieDto>) {
        movieDao.replace(movieDto)
    }

    override suspend fun getLatestUpdatedTime(): Long =
        movieRemoteKeyDao.getLatestMovieRemoteKey()?.lastUpdated
            ?: System.currentTimeMillis()

    override suspend fun deleteAll() {
        movieRemoteKeyDao.deleteAll()
        movieDao.deleteAll()
    }

    override suspend fun replaceRemoteKey(remoteKey: List<MovieRemoteKeyDto>) {
        movieRemoteKeyDao.replace(remoteKey)
    }

    override suspend fun getRemoteKey(id: Long): MovieRemoteKeyDto? =
        movieRemoteKeyDao.getRemoteKey(id)

    override fun getAllMovies(): PagingSource<Int, MovieDto> = movieDao.getAllMovies()
}