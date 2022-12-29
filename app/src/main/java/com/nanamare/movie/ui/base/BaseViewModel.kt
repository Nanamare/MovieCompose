package com.nanamare.movie.ui.base

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

interface BaseViewModel

// Activity ViewModel Scope
@SuppressLint("CompositionLocalNaming")
private val ActivityViewModelFactory = compositionLocalOf<BaseViewModel> {
    error("Not ViewModel Provided")
}

fun provideActivityViewModelFactory(baseViewModel: BaseViewModel) =
    ActivityViewModelFactory provides baseViewModel

@Composable
fun <T : BaseViewModel> getActivityViewModel(): T = ActivityViewModelFactory.current as T


// ViewModel Factory using current viewModelStoreOwner
@SuppressLint("CompositionLocalNaming")
private val ViewModelFactory = compositionLocalOf<@Composable () -> BaseViewModel> {
    { error("Not ViewModel Provided") }
}

fun provideViewModelFactory(viewModelFactory: @Composable () -> BaseViewModel) =
    ViewModelFactory provides viewModelFactory

@Composable
fun <T : BaseViewModel> getViewModel(): T = ViewModelFactory.current() as T

