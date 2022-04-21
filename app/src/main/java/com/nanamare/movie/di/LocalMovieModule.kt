package com.nanamare.movie.di

import com.nanamare.data.local.db.MovieLocalRepositoryImpl
import com.nanamare.domain.repository.MovieLocalRepository
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