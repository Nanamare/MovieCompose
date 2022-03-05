package com.nanamare.domain.model

data class MovieResponseModel(
    val dates: DatesModel?,
    val page: Int,
    val movies: List<MovieModel>,
    val totalPages: Int,
    val totalResults: Int
)

data class DatesModel(
    val maximum: String?,
    val minimum: String?
)

data class MovieModel(
    val primaryKey: Long,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Long,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)