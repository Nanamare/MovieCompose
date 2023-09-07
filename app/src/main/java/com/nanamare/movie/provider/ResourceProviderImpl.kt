package com.nanamare.movie.provider

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

class ResourceProviderImpl(
    @ApplicationContext private val context: Context
) : ResourceProvider {

    override fun getString(id: Int): String = context.getString(id)
}
