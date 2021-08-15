package com.saj.rickandmorty

import com.google.common.truth.Truth.assertThat
import com.saj.rickandmorty.builders.ShowCharacterDTOBuilder
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.callAdapter.NetworkResponseAdapterFactory
import com.saj.rickandmorty.network.callAdapter.NetworkResponseCall
import com.saj.rickandmorty.network.dtos.InfoDTO
import com.saj.rickandmorty.network.responses.NetworkResponse
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
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
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

        val showCharacterDTO = ShowCharacterDTOBuilder()
            .setId(1)
            .setName("Rick Sanchez")
            .setStatus("Alive")
            .setImageUrl("https://rickandmortyapi.com/api/character/avatar/1.jpeg")
            .setEpisodes(listOf("1", "2"))
            .setSpecies("Human")
            .build()

        val actual = (webService.getShowCharacters(null) as NetworkResponse.Success)
            .body.showCharacters
        val expected = listOf(showCharacterDTO)
        assertThat(expected).isEqualTo(actual)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should fetch info correctly given 200 response`() = runBlocking {
        mockWebServer.enqueue(getMockResponse(readJsonResponseAsString("1-character-200.json"),
            200))

        val actual = (webService.getShowCharacters(null) as NetworkResponse.Success)
            .body.info
        val expected = InfoDTO(nextPage = "next_page")

        assertThat(expected).isEqualTo(actual)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should fetch error message when 500 response`() = runBlocking {
        mockWebServer.enqueue(getMockResponse(readJsonResponseAsString("500.json"),
            500))

        val actual = (webService.getShowCharacters(null) as NetworkResponse.Error).message
        val expected = NetworkResponseCall.ERROR_MSG

        assertThat(expected).isEqualTo(actual)
    }
}