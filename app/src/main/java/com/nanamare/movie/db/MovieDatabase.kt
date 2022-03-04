package com.nanamare.movie.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nanamare.data.model.MovieRemoteKeyDto
import com.nanamare.data.model.MovieDto

@Database(
    entities = [MovieDto::class, MovieRemoteKeyDto::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    abstract fun getMovieRemoteKeyDao(): MovieRemoteKeyDao

}