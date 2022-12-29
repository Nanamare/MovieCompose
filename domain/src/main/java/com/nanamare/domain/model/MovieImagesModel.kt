package com.nanamare.domain.model

data class MovieImagesModel(
    val backdrops: List<BackdropModel>,
    val id: Int,
    val posters: List<PosterModel>
)

data class BackdropModel(
    val aspectRatio: Double,
    val filePath: String,
    val height: Int,
    val iso6391: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val width: Int
)

data class PosterModel(
    val aspectRatio: Double,
    val filePath: String,
    val height: Int,
    val iso6391: String,
    val voteAverage: Double,
    val voteCount: Int,
    val width: Int
)