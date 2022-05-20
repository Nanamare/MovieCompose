package com.nanamare.movie

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.nanamare.movie.ui.MainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * Reference https://github.com/android/nowinandroid
 */
@HiltAndroidTest
class NavigationTest {

    /**
     * Manages the components' state and is used to perform injection on your test
     */
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    /**
     * Use the primary activity to initialize the app normally.
     */
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Create a temporary folder used to create a Data Store file. This guarantees that
     * the file is removed in between each test, preventing a crash.
     */
    @BindValue
    @get:Rule(order = 2)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {

    }

    @Test
    fun is_first_screen_up_coming() {
        composeTestRule.apply {
            onNodeWithContentDescription("Upcoming").assertIsSelected()
        }
    }

    @Test
    fun is_displayed_movie_search_in_first_screen() {
        composeTestRule.apply {
            onNodeWithText("Movie Search", ignoreCase = true).assertIsDisplayed()
        }
    }

    @Test
    fun is_second_screen_trending() {
        composeTestRule.apply {
            onNodeWithContentDescription("Trending")
                .assertIsDisplayed()
                .assertHasClickAction()
                .performClick()
                .assertIsSelected()
        }
    }

    @Test
    fun is_third_screen_genre() {
        composeTestRule.apply {
            onNodeWithContentDescription("Genre")
                .assertIsDisplayed()
                .assertHasClickAction()
                .performClick()
                .assertIsSelected()
        }
    }

}