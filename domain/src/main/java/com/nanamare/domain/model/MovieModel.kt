package com.nanamare.domain.model

import kotlin.random.Random

data class MovieModel(
    val dates: DatesModel?,
    val page: Int,
    val results: List<ResultModel>,
    val totalPages: Int,
    val totalResults: Int
)

data class DatesModel(
    val maximum: String?,
    val minimum: String?
)

data class ResultModel(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Long = Random.nextLong(Long.MAX_VALUE),
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