package com.nanamare.movie.di

import com.nanamare.movie.db.MovieLocalRepository
import com.nanamare.movie.db.MovieLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class LocalMovieModule {

    @Singleton
    @Provides
    fun provideLocalRepository(localRepositoryImpl: MovieLocalRepositoryImpl): MovieLocalRepository =
        localRepositoryImpl

}