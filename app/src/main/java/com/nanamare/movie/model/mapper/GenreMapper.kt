package com.nanamare.movie.model.mapper

import com.nanamare.domain.model.GenreModel
import com.nanamare.movie.model.Genre

fun GenreModel.toVo() = Genre(id.toString(), name)