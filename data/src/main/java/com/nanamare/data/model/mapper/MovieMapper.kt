package com.nanamare.data.model.mapper

import com.nanamare.data.local.db.MovieEntity
import com.nanamare.data.local.db.MovieRemoteKeyEntity
import com.nanamare.data.model.BackdropDto
import com.nanamare.data.model.DatesDto
import com.nanamare.data.model.MovieDto
import com.nanamare.data.model.MovieImagesDto
import com.nanamare.data.model.MovieResponseDto
import com.nanamare.data.model.PosterDto
import com.nanamare.domain.model.BackdropModel
import com.nanamare.domain.model.DatesModel
import com.nanamare.domain.model.MovieImagesModel
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.model.MovieRemoteKeyModel
import com.nanamare.domain.model.MovieResponseModel
import com.nanamare.domain.model.PosterModel

fun MovieImagesDto.toDomainModel() = MovieImagesModel(backdrops.map(BackdropDto::toDomainModel), id, posters.map(PosterDto::toDomainModel))

fun BackdropDto.toDomainModel() = BackdropModel(aspectRatio, filePath, height, iso6391, voteAverage, voteCount, width)

fun PosterDto.toDomainModel() = PosterModel(aspectRatio, filePath, height, iso6391, voteAverage, voteCount, width)

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
    0,
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

fun MovieRemoteKeyEntity.toDomainModel() = MovieRemoteKeyModel(id, prevPage, nextPage, lastUpdated)

fun MovieModel.toEntity() = MovieEntity(
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

fun MovieEntity.toDomainModel() = MovieModel(
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
