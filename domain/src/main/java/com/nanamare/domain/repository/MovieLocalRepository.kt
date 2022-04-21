package com.nanamare.domain.repository

import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.model.MovieRemoteKeyModel

interface MovieLocalRepository {
    suspend fun replaceMovies(movieModels: List<MovieModel>)
    suspend fun getLatestUpdatedTime(): Long
    suspend fun deleteAll()
    suspend fun replaceRemoteKey(remoteKeys: List<MovieRemoteKeyModel>)
    suspend fun getRemoteKey(id: Long): MovieRemoteKeyModel?
}
