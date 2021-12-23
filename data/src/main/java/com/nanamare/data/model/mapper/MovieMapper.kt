package com.nanamare.data.model.mapper

import com.nanamare.data.model.DatesDto
import com.nanamare.data.model.MovieDto
import com.nanamare.data.model.ResultDto
import com.nanamare.domain.model.DatesModel
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.model.ResultModel


fun MovieDto.toDomainModel() =
    MovieModel(dates?.toDomainModel(), page, results.map(ResultDto::toDomainModel), totalPages, totalResults)

fun DatesDto.toDomainModel() = DatesModel(maximum, minimum)

fun ResultDto.toDomainModel() = ResultModel(
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
