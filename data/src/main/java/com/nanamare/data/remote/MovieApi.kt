package com.nanamare.data.remote

import android.annotation.SuppressLint
import com.nanamare.data.model.GenreListDto
import com.nanamare.data.model.MovieImagesDto
import com.nanamare.data.model.MovieResponseDto
import com.nanamare.data.utils.Cacheable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MovieApi {

    @GET("3/movie/upcoming")
    fun getUpcomingMovie(@Query("page") page: Int): Call<MovieResponseDto>

    @GET("3/search/movie")
    fun searchMovie(@Query("page") page: Int, @Query("query") query: String): Call<MovieResponseDto>

    @SuppressLint("NewApi")
    @GET("3/genre/movie/list")
    @Cacheable(1, TimeUnit.HOURS)
    fun getGenreList(): Call<GenreListDto>

    @GET("3/discover/movie")
    fun getGenreMovies(
        @Query("page") page: Int,
        @Query("with_genres") withGenres: String
    ): Call<MovieResponseDto>

    // GET /trending/{media_type}/{time_window}
    // media_type = movie
    // time_window = day, week
    @GET("3/trending/{media_type}/{time_window}")
    fun getTrendingMovie(
        @Path("media_type") mediaType: String = "movie",
        @Path("time_window") timeWindow: String = "week",
        @Query("page") page: Int
    ): Call<MovieResponseDto>

    @GET("3/movie/{movieId}/images")
    fun getMovieImages(@Path("movieId") movieId: String): Call<MovieImagesDto>

}