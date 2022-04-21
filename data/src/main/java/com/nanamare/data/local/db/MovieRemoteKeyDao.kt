package com.nanamare.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieRemoteKeyDao {

    @Query("SELECT * FROM movie_remote_key WHERE id = :id")
    suspend fun getRemoteKey(id: Long): MovieRemoteKeyEntity?

    @Query("SELECT * FROM movie_remote_key ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getLatestMovieRemoteKey(): MovieRemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replace(movieRemoteKeys: List<MovieRemoteKeyEntity>)

    @Query("DELETE FROM movie_remote_key")
    suspend fun deleteAll()

}