package com.nanamare.movie.di.app

import com.nanamare.movie.ui.NavigationViewModelImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class ViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideNavigationViewModel(navigationViewModelImpl: NavigationViewModelImpl): NavigationViewModelImpl =
        navigationViewModelImpl
}
