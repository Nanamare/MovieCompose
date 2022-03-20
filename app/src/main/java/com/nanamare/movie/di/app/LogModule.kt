package com.nanamare.movie.di.app

import com.nanamare.domain.provider.Log
import com.nanamare.movie.provider.LogImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LogModule {

    @Provides
    @Singleton
    fun provideLog(): Log = LogImpl()

}