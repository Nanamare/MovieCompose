package com.nanamare.data.api

import com.google.common.truth.Truth.assertThat
import com.nanamare.data.remote.MovieApi
import com.nanamare.test_shared.BaseApiTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.await

@OptIn(ExperimentalCoroutinesApi::class)
class MovieServiceTest : BaseApiTest<MovieApi>() {

    private lateinit var movieApi: MovieApi

    override fun initService() {
        movieApi = createService(MovieApi::class.java)
    }

    @Test
    fun test_request_search_movie_with_query_harry_potter() = runTest {
        enqueueResponse("SearchMovieResponse.json")

        val response = movieApi.searchMovie(1, "harry potter").await()

        assertThat(response).isNotNull()
        assertThat(response.page).isEqualTo(2)
        assertThat(response.results.size).isEqualTo(7)
        assertThat(response.totalPages).isEqualTo(2)
        assertThat(response.totalResults).isEqualTo(27)
    }

    @Test
    fun test_request_up_coming_movie() = runTest {
        enqueueResponse("UpcomingMovieResponse.json")

        val response = movieApi.getUpcomingMovie(1).await()

        assertThat(response).isNotNull()
        assertThat(response.page).isEqualTo(1)
        assertThat(response.results.size).isEqualTo(20)
        assertThat(response.totalPages).isEqualTo(14)
        assertThat(response.totalResults).isEqualTo(274)
    }

    @Test
    fun test_request_genre_list() = runTest {
        enqueueResponse("GenreResponse.json")

        val response = movieApi.getGenreList().await()

        assertThat(response).isNotNull()
        assertThat(response.genres.size).isEqualTo(19)
        assertThat(response.genres[0].id).isEqualTo(28)
        assertThat(response.genres[0].name).isEqualTo("Action")
        assertThat(response.genres[10].id).isEqualTo(27)
        assertThat(response.genres[10].name).isEqualTo("Horror")
    }

}