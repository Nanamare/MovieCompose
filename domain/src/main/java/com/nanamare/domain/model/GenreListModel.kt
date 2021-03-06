package com.nanamare.domain.model

data class GenreListModel(val genres: List<GenreModel>)

data class GenreModel(val id: Long, val name: String)