package com.nanamare.data.model.mapper

import com.nanamare.data.source.local.db.MovieRemoteKeyEntity
import com.nanamare.domain.model.MovieRemoteKeyModel

fun MovieRemoteKeyModel.toEntity() = MovieRemoteKeyEntity(id, prevPage, nextPage, lastUpdated)