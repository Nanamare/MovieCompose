package com.nanamare.movie.di.app

import com.nanamare.domain.provider.Log
import com.nanamare.movie.provider.LogImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface LogModule {

    @Binds
    @Singleton
    fun provideLog(logImpl: LogImpl): Log
}