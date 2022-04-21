package com.nanamare.domain.model

data class MovieRemoteKeyModel(
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long
)