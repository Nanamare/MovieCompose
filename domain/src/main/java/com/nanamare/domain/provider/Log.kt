package com.nanamare.domain.provider

interface Log {
    fun d(log: String)
    fun e(e: Throwable)
}