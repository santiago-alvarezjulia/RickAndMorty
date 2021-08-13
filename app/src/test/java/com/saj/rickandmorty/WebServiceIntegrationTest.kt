package com.saj.rickandmorty

import com.google.common.truth.Truth.assertThat
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.dtos.InfoDTO
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.saj.rickandmorty.testUtils.UnitTestsUtils.getMockResponse
import com.saj.rickandmorty.testUtils.UnitTestsUtils.readJsonResponseAsString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WebServiceIntegrationTest {

    private val mockWebServer = MockWebServer()

    private val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val webService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(RickAndMortyWebService::class.java)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should fetch show characters correctly given 200 response`() = runBlocking {
        mockWebServer.enqueue(getMockResponse(readJsonResponseAsString("1-character-200.json"),
            200))

        val actual = webService.getShowCharacters(null).showCharacters
        val expected = listOf(
            ShowCharacterDTO(
                id = 1,
                name = "Rick Sanchez",
                status = "Dead",
                imageUrl = "image_url",
                episodes = listOf("1", "2")
            )
        )
        assertThat(expected).isEqualTo(actual)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should fetch info correctly given 200 response`() = runBlocking {
        mockWebServer.enqueue(getMockResponse(readJsonResponseAsString("1-character-200.json"),
            200))

        val actual = webService.getShowCharacters(null).info
        val expected = InfoDTO(nextPage = "next_page")

        assertThat(expected).isEqualTo(actual)
    }
}