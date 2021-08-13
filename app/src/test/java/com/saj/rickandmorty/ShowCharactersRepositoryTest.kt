package com.saj.rickandmorty

import com.google.common.truth.Truth
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.network.RickAndMortyWebService
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
        val showCharacter = ShowCharacterDTO(1, "Rick Sanchez", "Dead",
            "image_url", listOf("1", "2"))
        stubWebService(listOf(showCharacter))
        every { listMapper.map(any()) } returns listOf(ShowCharacter(1, "Rick Sanchez",
            "Dead", "image_url", 2))
        val charactersListRepository = ShowCharactersRepository(rickAndMortyWebService, listMapper)
        val characters = charactersListRepository.fetchShowCharacters()
        Truth.assertThat(characters.isEmpty()).isFalse()
    }

    private fun stubWebService(showCharacters: List<ShowCharacterDTO>) {
        val response = GetCharactersResponse(showCharacters)
        coEvery { rickAndMortyWebService.getShowCharacters()} returns response
    }
}