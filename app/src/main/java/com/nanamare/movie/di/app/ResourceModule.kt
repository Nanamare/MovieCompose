package com.nanamare.movie.di.app

import com.nanamare.movie.provider.ResourceProvider
import com.nanamare.movie.provider.ResourceProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ResourceModule {

    @Singleton
    @Binds
    abstract fun provideResource(provider: ResourceProviderImpl): ResourceProvider
}
