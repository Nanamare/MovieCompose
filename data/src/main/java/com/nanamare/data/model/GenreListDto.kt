package com.nanamare.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreListDto(
    @SerialName("genres")
    val genres: List<GenreDto>
)

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)