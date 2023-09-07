package com.nanamare.movie.di.app

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nanamare.base.debug.Flipper
import com.nanamare.base.util.NetworkConnection
import com.nanamare.base.util.NetworkConnectionImpl
import com.nanamare.data.BuildConfig
import com.nanamare.data.source.remote.interceptor.AuthenticationInterceptor
import com.nanamare.data.source.remote.interceptor.CacheInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClientBuilder() = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .also(Flipper::initOkHttpClient)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Named("kotlinx")
    fun provideKotlinxSerializationJson() = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        prettyPrint = true
        explicitNulls = false
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    @Named("retrofit_tmdb")
    fun provideTMDBRetrofit(
        httpClient: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor,
        cacheInterceptor: CacheInterceptor,
        @Named("kotlinx") json: Json,
        @ApplicationContext context: Context
    ): Retrofit {
        val builder =
            httpClient.addInterceptor(authenticationInterceptor).addInterceptor(cacheInterceptor)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }

        val cache = Cache(context.cacheDir, (5 * 1024 * 1024).toLong()) // 5MB

        val client = builder.cache(cache).build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }


    @InstallIn(SingletonComponent::class)
    @Module
    interface Binder {
        @Binds
        @Singleton
        fun bindNetworkConnection(connection: NetworkConnectionImpl): NetworkConnection
    }

    companion object {
        private const val TIME_OUT = 30L
    }
}