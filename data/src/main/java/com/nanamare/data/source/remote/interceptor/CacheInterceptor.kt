package com.nanamare.data.source.remote.interceptor

import com.nanamare.data.utils.Cacheable
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class CacheInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request.tag(Invocation::class.java)?.method()
            ?.getAnnotation(Cacheable::class.java)
            ?.let { cacheable: Cacheable ->

                val cacheControl = CacheControl.Builder()
                    .maxStale(cacheable.value, cacheable.timeUnit)
                    .build()

                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA) // PRAGMA 제거
                    .removeHeader(HEADER_CACHE_CONTROL) // Cache-Control 제거
                    .cacheControl(cacheControl)
                    .build()
            }

        return chain.proceed(request)
    }

    companion object {
        // https://github.com/square/okhttp/blob/e1d5d5f5206064efebe5ef6842cdfa4b94745805/okhttp/src/commonMain/kotlin/okhttp3/internal/-CacheControlCommon.kt#L142 참고
        private const val HEADER_CACHE_CONTROL = "Cache-Control"
        private const val HEADER_PRAGMA = "Pragma"
    }
}