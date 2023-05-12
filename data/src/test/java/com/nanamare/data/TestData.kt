package com.nanamare.data

import com.nanamare.data.source.local.db.MovieEntity
import com.nanamare.data.source.local.db.MovieRemoteKeyEntity
import com.nanamare.data.model.*
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.model.MovieRemoteKeyModel
import java.time.Instant

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

        val movieEntity = MovieEntity(
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

        val movieModel = MovieModel(
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

        val movieRemoteKeyEntity = MovieRemoteKeyEntity(
            id = 7,
            prevPage = 1,
            nextPage = 3,
            lastUpdated = Instant.EPOCH.epochSecond
        )

        val movieRemoteKeyModel = MovieRemoteKeyModel(
            id = 7,
            prevPage = 1,
            nextPage = 3,
            lastUpdated = Instant.EPOCH.epochSecond
        )
    }

    object Genre {
        val actionGenreDto = GenreDto(id = 0, name = "Action")
        val comedyGenreDto = GenreDto(id = 1, name = "Comedy")

        val genreListDto = GenreListDto(listOf(actionGenreDto, comedyGenreDto))
    }

}