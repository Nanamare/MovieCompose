package com.nanamare.movie.di

import com.nanamare.domain.di.IoDispatcher
import com.nanamare.domain.di.MainImmediateDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class CoroutinesModule {

    @IoDispatcher
    @Provides
    fun providesIoDispatcher() = Dispatchers.IO

    @MainImmediateDispatcher
    @Provides
    fun providesMainImmediateDispatcher() = Dispatchers.Main.immediate
}