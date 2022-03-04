package com.nanamare.movie.di

import android.content.Context
import androidx.room.Room
import com.nanamare.movie.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, MOVIE_TABLE_NAME)
            .build()

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase) = movieDatabase.getMovieDao()

    @Provides
    fun provideMovieRemoteKeyDao(movieDatabase: MovieDatabase) =
        movieDatabase.getMovieRemoteKeyDao()

    companion object {
        private const val MOVIE_TABLE_NAME = "movie.db"
    }
}