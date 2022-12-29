package com.nanamare.movie.ui.genre

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nanamare.base.ui.compose.setThemeContent
import com.nanamare.movie.ui.screen.GenreDetailScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GenreDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: GenreDetailViewModel.Factory

    private val viewModel: GenreDetailViewModel by viewModels<GenreDetailViewModelImpl> {
        GenreDetailViewModelFactory(factory, this, intent?.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setThemeContent {
            GenreDetailScreen(viewModel)
        }
    }


    companion object {
        const val EXTRA_GENRE_KEY = "extra_genre_key"
    }
}