package com.nanamare.movie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.nanamare.base.ui.compose.setThemeContent
import com.nanamare.movie.ui.screen.NavigationScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setThemeContent {
            CompositionLocalProvider(
                provideViewModelFactory(hiltViewModel<MainActivityViewModelImpl>())
            ) {
                NavigationScreen(navController = rememberNavController())
            }
        }
    }

}