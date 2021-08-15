package com.saj.rickandmorty

import com.google.common.truth.Truth
import com.saj.rickandmorty.builders.InfoDTOBuilder
import com.saj.rickandmorty.builders.ShowCharacterBuilder
import com.saj.rickandmorty.builders.ShowCharacterDTOBuilder
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.dtos.InfoDTO
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.saj.rickandmorty.network.mappers.ListMapper
import com.saj.rickandmorty.network.responses.GetCharactersResponse
import com.saj.rickandmorty.network.responses.NetworkResponse
import com.saj.rickandmorty.repositories.ShowCharactersRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ShowCharactersRepositoryTest {

    private val rickAndMortyWebService = mockk<RickAndMortyWebService>()
    private val listMapper = mockk<ListMapper<ShowCharacterDTO, ShowCharacter>>()

    @ExperimentalCoroutinesApi
    @Test
    fun `get show characters returns list of characters`() = runBlockingTest {
        val showCharacter = ShowCharacterDTOBuilder().build()
        val info = InfoDTOBuilder().build()
        stubWebService(listOf(showCharacter), info)
        stubCharactersListMapper()
        val charactersListRepository = ShowCharactersRepository(rickAndMortyWebService, listMapper)
        val response = charactersListRepository.fetchNewShowCharactersPage(null)
        val newCharactersPage = (response as NetworkResponse.Success).body
        Truth.assertThat(newCharactersPage.showCharacters.isEmpty()).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when nextPageUrl null, getNextPage returns null`() = runBlockingTest {
        val showCharacter = ShowCharacterDTOBuilder().build()
        val emptyInfo = InfoDTOBuilder().setNextPage(null).build()
        stubWebService(listOf(showCharacter), emptyInfo)
        stubCharactersListMapper()
        val charactersListRepository = ShowCharactersRepository(rickAndMortyWebService, listMapper)
        val response = charactersListRepository.fetchNewShowCharactersPage(null)
        val newCharactersPage = (response as NetworkResponse.Success).body
        Truth.assertThat(newCharactersPage.nextPage).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when nextPageUrl have value, getNextPage returns that value`() = runBlockingTest {
        val showCharacter = ShowCharacterDTOBuilder().build()
        val nextPage = "20"
        val info = InfoDTOBuilder()
            .setNextPage("https://rickandmortyapi.com/api/character/?page=$nextPage")
            .build()
        stubWebService(listOf(showCharacter), info)
        stubCharactersListMapper()
        val charactersListRepository = ShowCharactersRepository(rickAndMortyWebService, listMapper)
        val response = charactersListRepository.fetchNewShowCharactersPage(null)
        val newCharactersPage = (response as NetworkResponse.Success).body
        Truth.assertThat(newCharactersPage.nextPage).isEqualTo(nextPage)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when nextPageUrl is malformed, getNextPage returns null`() = runBlockingTest {
        val showCharacter = ShowCharacterDTOBuilder().build()
        val info = InfoDTOBuilder()
            .setNextPage("malformed_url")
            .build()
        stubWebService(listOf(showCharacter), info)
        stubCharactersListMapper()
        val charactersListRepository = ShowCharactersRepository(rickAndMortyWebService, listMapper)
        val response = charactersListRepository.fetchNewShowCharactersPage(null)
        val newCharactersPage = (response as NetworkResponse.Success).body
        Truth.assertThat(newCharactersPage.nextPage).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when network response is error, return it as error`() = runBlockingTest {
        val errorMessage = "Error msg"
        stubWebServiceError(errorMessage)
        val charactersListRepository = ShowCharactersRepository(rickAndMortyWebService, listMapper)
        val response = charactersListRepository.fetchNewShowCharactersPage(null)
        Truth.assertThat((response as NetworkResponse.Error).message).isEqualTo(errorMessage)
    }

    private fun stubCharactersListMapper() {
        val showCharacter = ShowCharacterBuilder().build()
        every { listMapper.map(any()) } returns listOf(showCharacter)
    }

    private fun stubWebService(showCharacters: List<ShowCharacterDTO>, info: InfoDTO) {
        val response = GetCharactersResponse(showCharacters, info)
        coEvery { rickAndMortyWebService.getShowCharacters(any())} returns NetworkResponse.Success(response)
    }

    private fun stubWebServiceError(errorMessage: String) {
        coEvery { rickAndMortyWebService.getShowCharacters(any())} returns NetworkResponse.Error(errorMessage)
    }
}