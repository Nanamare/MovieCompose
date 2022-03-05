package com.nanamare.movie.db

import androidx.paging.PagingSource
import com.nanamare.data.model.MovieDto
import com.nanamare.data.model.MovieRemoteKeyDto

interface MovieLocalRepository {
    suspend fun replaceMovies(movieDto: List<MovieDto>)
    suspend fun getLatestUpdatedTime(): Long
    suspend fun deleteAll()
    suspend fun replaceRemoteKey(remoteKey: List<MovieRemoteKeyDto>)
    suspend fun getRemoteKey(id: Long): MovieRemoteKeyDto?
    fun getAllMovies(): PagingSource<Int, MovieDto>
}
