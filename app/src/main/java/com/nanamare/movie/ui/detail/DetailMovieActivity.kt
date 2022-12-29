package com.nanamare.movie.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nanamare.base.ui.compose.setThemeContent
import com.nanamare.movie.model.Movie
import com.nanamare.movie.ui.screen.DetailMovieScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    @Inject
    lateinit var detailMovieViewModelFactory: DetailMovieViewModel.Factory

    private val movie by lazy {
        checkNotNull(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)
            } else {
                intent.getParcelableExtra(EXTRA_MOVIE)
            }
        ) {
            "empty movie"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setThemeContent {
            DetailMovieScreen(movie, detailMovieViewModelFactory.create(movie.id.toString()))
        }
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

}