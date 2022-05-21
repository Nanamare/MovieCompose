package com.nanamare.movie.di

import com.nanamare.data.local.db.MovieLocalRepositoryImpl
import com.nanamare.domain.repository.MovieLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface LocalMovieModule {

    @Binds
    fun provideLocalRepository(localRepositoryImpl: MovieLocalRepositoryImpl): MovieLocalRepository

}