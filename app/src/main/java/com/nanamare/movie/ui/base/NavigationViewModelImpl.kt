package com.nanamare.movie.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

open class NavigationViewModelImpl : NavigationViewModel, ViewModel() {

    private val _navigation = Channel<NavigationViewModel.Screen>(capacity = Channel.CONFLATED)
    override val navigation = _navigation.receiveAsFlow()

    override fun navigate(screen: NavigationViewModel.Screen) {
        _navigation.trySend(screen)
    }

}