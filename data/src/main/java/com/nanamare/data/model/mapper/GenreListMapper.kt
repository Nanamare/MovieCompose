package com.nanamare.data.model.mapper

import com.nanamare.data.model.GenreDto
import com.nanamare.data.model.GenreListDto
import com.nanamare.domain.model.GenreListModel
import com.nanamare.domain.model.GenreModel

fun GenreListDto.toDomainModel() = GenreListModel(genres.map(GenreDto::toDomainModel))

fun GenreDto.toDomainModel() = GenreModel(id, name)