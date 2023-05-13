package com.nanamare.base.debug

import android.content.Context
import okhttp3.OkHttpClient

object Flipper {
    fun init(context: Context) {}
    fun initOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) {}
}