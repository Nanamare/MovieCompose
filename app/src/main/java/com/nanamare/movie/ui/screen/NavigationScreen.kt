package com.nanamare.movie.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nanamare.base.util.rememberFlowWithLifecycle
import com.nanamare.movie.model.mapper.toVo
import com.nanamare.movie.ui.MainActivityViewModel
import com.nanamare.movie.ui.base.NavigationViewModel
import com.nanamare.movie.ui.base.getActivityViewModel
import com.nanamare.movie.ui.detail.DetailMovieActivity
import com.nanamare.movie.ui.genre.GenreDetailActivity
import com.nanamare.movie.ui.genre.GenreDetailActivity.Companion.EXTRA_GENRE_KEY
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun NavigationScreen(
    navController: NavHostController,
    viewModel: MainActivityViewModel = getActivityViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { state ->
            SnackbarHost(hostState = state) { data ->
                Snackbar(actionColor = Color.White, snackbarData = data)
            }
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomType.values().forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = screen.route) },
                        label = { Text(screen.name) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = { onBottomClick(navController, screen) })
                }
            }
        }
    ) { innerPadding ->
        CompositionLocalProvider(LocalScaffoldState provides scaffoldState) {
            NavHost(navController = navController, startDestination = BottomType.Upcoming.route) {
                composable(BottomType.Upcoming.route) {
                    UpcomingScreen(modifier = Modifier.padding(innerPadding))
                }
                composable(BottomType.Trending.route) {
                    TrendingScreen(modifier = Modifier.padding(innerPadding))
                }
                composable(BottomType.Genre.route) {
                    GenreScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    val context = LocalContext.current
    val navigationActions = rememberFlowWithLifecycle(viewModel.navigation)
    LaunchedEffect(navigationActions) {
        navigationActions.collect { screen ->
            when (screen) {
                is NavigationViewModel.Screen.DetailMovie -> {
                    context.startActivity(
                        Intent(context, DetailMovieActivity::class.java)
                            .putExtra(DetailMovieActivity.EXTRA_MOVIE, screen.movie)
                    )
                }

                is NavigationViewModel.Screen.GenreDetail -> {
                    context.startActivity(
                        Intent(context, GenreDetailActivity::class.java)
                            .putExtra(EXTRA_GENRE_KEY, screen.genreModel.toVo())
                    )
                }
            }
        }
    }
}

private fun onBottomClick(
    navController: NavHostController,
    screen: BottomType
) {
    navController.navigate(screen.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

enum class BottomType(val route: String, val icon: ImageVector) {
    Upcoming("Upcoming", Icons.Filled.Favorite),
    Trending("Trending", Icons.Filled.ThumbUp),
    Genre("Genre", Icons.Filled.Star)
}

val LocalScaffoldState = compositionLocalOf<ScaffoldState> { error("Not provided scaffoldState") }