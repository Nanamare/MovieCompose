package com.nanamare.movie.model.mapper

import com.nanamare.domain.model.ResultModel
import com.nanamare.movie.model.Result

// View Object
fun ResultModel.toVo() = Result(
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