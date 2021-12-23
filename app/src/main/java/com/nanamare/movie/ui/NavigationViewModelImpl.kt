package com.nanamare.movie.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

open class NavigationViewModelImpl @Inject constructor() : NavigationViewModel, ViewModel() {

    private val _navigation = Channel<NavigationViewModel.Screen>(capacity = Channel.CONFLATED)
    override val navigation: Flow<NavigationViewModel.Screen> = _navigation.receiveAsFlow()

    override fun navigate(screen: NavigationViewModel.Screen) {
        _navigation.trySend(screen)
    }
}