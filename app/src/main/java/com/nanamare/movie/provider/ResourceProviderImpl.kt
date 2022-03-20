package com.nanamare.movie.provider

import android.content.Context

class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override fun getString(id: Int): String = context.getString(id)

}