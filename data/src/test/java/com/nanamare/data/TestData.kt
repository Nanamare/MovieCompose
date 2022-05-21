package com.nanamare.data

import com.nanamare.data.model.DatesDto
import com.nanamare.data.model.MovieDto
import com.nanamare.data.model.MovieResponseDto

object TestData {

    object Movie {
        val datesDto = DatesDto(maximum = "2022-05-21", minimum = "2022-04-21")

        val movieDto = MovieDto(
            primaryKey = 100,
            adult = true,
            backdropPath = "backdropPath",
            genreIds = listOf(1, 2, 3),
            id = 50,
            originalLanguage = "EN",
            originalTitle = "Matrix",
            overview = "String recommended",
            popularity = 9.0,
            posterPath = "posterPath",
            releaseDate = "2022-05-21",
            title = "Matrix",
            video = true,
            voteAverage = 7.7,
            voteCount = 5111
        )

        val movieResponseDto = MovieResponseDto(
            dates = datesDto,
            page = 1,
            results = listOf(movieDto, movieDto),
            totalPages = 1,
            totalResults = 2
        )
    }

}