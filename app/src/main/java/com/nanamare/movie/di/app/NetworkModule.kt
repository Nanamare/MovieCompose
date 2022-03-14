package com.nanamare.movie.di.app

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nanamare.base.util.NetworkConnection
import com.nanamare.base.util.NetworkConnectionImpl
import com.nanamare.data.remote.interceptor.AuthenticationInterceptor
import com.nanamare.movie.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
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
    fun httpClientBuilder() = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    @Named("retrofit_tmdb")
    fun provideTMDBRetrofit(
        httpClient: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor
    ): Retrofit {
        val builder = httpClient.addInterceptor(authenticationInterceptor)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }

        val client = builder.build()

        val json = Json {
            isLenient = false
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkConnection(@ApplicationContext context: Context): NetworkConnection =
        NetworkConnectionImpl(context)

    companion object {
        private const val TIME_OUT = 30L
    }
}