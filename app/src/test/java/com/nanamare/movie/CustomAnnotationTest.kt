package com.nanamare.movie

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nanamare.data.BuildConfig.BASE_URL
import com.nanamare.data.remote.MovieApi
import com.nanamare.data.utils.Cacheable
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Invocation
import retrofit2.Retrofit
import retrofit2.http.GET
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CustomAnnotationTest {

    @Test(expected = Exception::class)
    @Throws(Exception::class)
    fun `Cacheable 어노테이션 동작 테스트`() {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor { message: String? -> Timber.i(message) }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(
                // Interceptor for UnitTest
                Interceptor { chain ->
                    // Then
                    val request: Request = chain.request()

                    assertEquals(GET::class.java.simpleName, request.method)

                    assertEquals("$BASE_URL/$GENRE_URL", request.url.toString())

                    val invocation = request.tag(Invocation::class.java)
                    assertNotNull(invocation)

                    val annotation: Cacheable? =
                        requireNotNull(invocation).method().getAnnotation(Cacheable::class.java)
                    assertNotNull(annotation)
                    assertEquals(
                        Cacheable::class.java.simpleName,
                        requireNotNull(annotation).annotationClass.simpleName
                    )

                    assertEquals(annotation.value, 1)
                    assertEquals(annotation.timeUnit, TimeUnit.HOURS)

                    throw Exception()
                })
            .build()

        // Given
        val json = Json {
            isLenient = false
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        // Given
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()

        val moveApi: MovieApi = retrofit.create(MovieApi::class.java)

        // When
        moveApi.getGenreList().execute()
    }

    companion object {
        private const val GENRE_URL = "3/genre/movie/list"
    }

}