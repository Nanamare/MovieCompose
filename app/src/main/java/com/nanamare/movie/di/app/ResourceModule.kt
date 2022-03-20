package com.nanamare.movie.di.app

import android.content.Context
import com.nanamare.movie.provider.ResourceProvider
import com.nanamare.movie.provider.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ResourceModule {

    @Singleton
    @Provides
    fun provideResource(@ApplicationContext context: Context): ResourceProvider =
        ResourceProviderImpl(context)

}