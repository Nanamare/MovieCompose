package com.nanamare.movie.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

interface BaseViewModel

@SuppressLint("CompositionLocalNaming")
private val ViewModelFactory = compositionLocalOf<@Composable () -> BaseViewModel> {
    { error("Not ViewModel Provided") }
}

fun provideViewModelFactory(viewModelFactory: @Composable () -> BaseViewModel) =
    ViewModelFactory provides viewModelFactory

@Composable
fun <T : BaseViewModel> getViewModel(): T = ViewModelFactory.current() as T