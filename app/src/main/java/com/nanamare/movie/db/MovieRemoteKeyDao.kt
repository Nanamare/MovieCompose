package com.nanamare.movie.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nanamare.data.model.MovieRemoteKeyDto

@Dao
interface MovieRemoteKeyDao {

    @Query("SELECT * FROM movie_remote_key WHERE id = :id")
    suspend fun getRemoteKey(id: Long): MovieRemoteKeyDto?

    @Query("SELECT * FROM movie_remote_key ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getLatestMovieRemoteKey(): MovieRemoteKeyDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKey(movieRemoteKeys: List<MovieRemoteKeyDto>)

    @Query("DELETE FROM movie_remote_key")
    suspend fun deleteAll()

}