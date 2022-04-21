package com.nanamare.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_remote_key")
data class MovieRemoteKeyEntity(
    @PrimaryKey
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long
)