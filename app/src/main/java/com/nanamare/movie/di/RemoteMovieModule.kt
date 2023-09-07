package com.nanamare.movie.di

import com.nanamare.data.source.remote.MovieApi
import com.nanamare.data.source.remote.impl.MovieRemoteRepositoryImpl
import com.nanamare.domain.repository.MovieRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteMovieModule {

    @Singleton
    @Provides
    fun provideMovieApi(@Named("retrofit_tmdb") retrofit: Retrofit): MovieApi = retrofit.create()

    @InstallIn(SingletonComponent::class)
    @Module
    interface Binder {
        @Singleton
        @Binds
        fun provideUpcomingMovieRepository(
            movieRemoteRepositoryImpl: MovieRemoteRepositoryImpl
        ): MovieRemoteRepository
    }
}