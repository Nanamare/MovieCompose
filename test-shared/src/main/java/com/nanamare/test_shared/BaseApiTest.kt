package com.nanamare.test_shared

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import retrofit2.Retrofit
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalSerializationApi::class)
abstract class BaseApiTest<T> {

    @Before
    abstract fun initService()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var mockWebServer: MockWebServer

    @Throws(IOException::class)
    @Before
    fun mockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Throws(IOException::class)
    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    @Throws(IOException::class, FileNotFoundException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream =
            javaClass.classLoader?.getResourceAsStream(fileName) ?: throw FileNotFoundException()
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }

    fun createService(clazz: Class<T>): T {
        val json = Json {
            explicitNulls = false
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
        }
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(clazz)
    }
}