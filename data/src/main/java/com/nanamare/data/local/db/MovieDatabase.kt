package com.nanamare.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, MovieRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    abstract fun getMovieRemoteKeyDao(): MovieRemoteKeyDao

}