package com.nanamare.data.model.mapper

import com.nanamare.data.model.DatesDto
import com.nanamare.data.model.MovieDto
import com.nanamare.data.model.MovieResponseDto
import com.nanamare.domain.model.DatesModel
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.model.MovieResponseModel


fun MovieResponseDto.toDomainModel() =
    MovieResponseModel(
        dates?.toDomainModel(),
        page,
        results.map(MovieDto::toDomainModel),
        totalPages,
        totalResults
    )

fun DatesDto.toDomainModel() = DatesModel(maximum, minimum)

fun MovieDto.toDomainModel() = MovieModel(
    primaryKey,
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)

fun MovieModel.toDto() = MovieDto(
    primaryKey,
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)
