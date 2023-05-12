package com.nanamare.data.source.local.db

import com.nanamare.data.model.mapper.toDomainModel
import com.nanamare.data.model.mapper.toEntity
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.model.MovieRemoteKeyModel
import com.nanamare.domain.repository.MovieLocalRepository
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieRemoteKeyDao: MovieRemoteKeyDao
) : MovieLocalRepository {

    override suspend fun replaceMovies(movieModels: List<MovieModel>) {
        movieDao.replace(movieModels.map(MovieModel::toEntity))
    }

    override suspend fun getLatestUpdatedTime(): Long =
        movieRemoteKeyDao.getLatestMovieRemoteKey()?.lastUpdated ?: System.currentTimeMillis()

    override suspend fun deleteAll() {
        movieRemoteKeyDao.deleteAll()
        movieDao.deleteAll()
    }

    override suspend fun replaceRemoteKey(remoteKeys: List<MovieRemoteKeyModel>) {
        movieRemoteKeyDao.replace(remoteKeys.map(MovieRemoteKeyModel::toEntity))
    }

    override suspend fun getRemoteKey(id: Long): MovieRemoteKeyModel? =
        movieRemoteKeyDao.getRemoteKey(id)?.toDomainModel()


}