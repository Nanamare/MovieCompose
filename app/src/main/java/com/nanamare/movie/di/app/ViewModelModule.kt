package com.nanamare.movie.di.app

import com.nanamare.movie.ui.base.NavigationViewModel
import com.nanamare.movie.ui.base.NavigationViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
interface ViewModelModule {

    @ViewModelScoped
    @get:[Binds]
    val NavigationViewModelImpl.bindNavigationViewModelImpl: NavigationViewModel

}
