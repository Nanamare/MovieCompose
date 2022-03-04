package com.nanamare.movie.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nanamare.data.model.MovieDto
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieDto>)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): PagingSource<Int, MovieDto>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovie(id: Long): Flow<MovieDto>

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

}