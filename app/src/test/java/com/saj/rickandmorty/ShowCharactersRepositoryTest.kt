package com.saj.rickandmorty

import com.google.common.truth.Truth
import com.saj.rickandmorty.builders.ShowCharacterBuilder
import com.saj.rickandmorty.builders.ShowCharacterDTOBuilder
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.dtos.InfoDTO
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.saj.rickandmorty.network.mappers.ListMapper
import com.saj.rickandmorty.network.responses.GetCharactersResponse
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
        val info = InfoDTO("https://rickandmortyapi.com/api/character/?page=2")
        stubWebService(listOf(showCharacter), info)
        stubCharactersListMapper()
        val charactersListRepository = ShowCharactersRepository(rickAndMortyWebService, listMapper)
        val newCharactersPage = charactersListRepository.fetchNewShowCharactersPage(null)
        Truth.assertThat(newCharactersPage.showCharacters.isEmpty()).isFalse()
    }

    private fun stubCharactersListMapper() {
        val showCharacter = ShowCharacterBuilder().build()
        every { listMapper.map(any()) } returns listOf(showCharacter)
    }

    private fun stubWebService(showCharacters: List<ShowCharacterDTO>, info: InfoDTO) {
        val response = GetCharactersResponse(showCharacters, info)
        coEvery { rickAndMortyWebService.getShowCharacters(any())} returns response
    }
}