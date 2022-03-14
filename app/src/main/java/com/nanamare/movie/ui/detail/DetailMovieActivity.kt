package com.nanamare.movie.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nanamare.base.ui.compose.setThemeContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setThemeContent {

        }
    }

}