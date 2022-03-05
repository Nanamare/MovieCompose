package com.nanamare.movie.model.mapper

import com.nanamare.data.model.MovieDto
import com.nanamare.domain.model.MovieModel
import com.nanamare.movie.model.Movie

fun MovieModel.toVo() = Movie(
    primaryKey,
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath.toString(),
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)

fun MovieDto.toVo() = Movie(
    primaryKey,
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath.toString(),
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)