package com.nanamare.movie.provider

import com.nanamare.domain.provider.Log
import timber.log.Timber

class LogImpl : Log {

    override fun d(log: String) {
        Timber.d(log)
    }

    override fun e(e: Throwable) {
        Timber.e(e)
    }
}