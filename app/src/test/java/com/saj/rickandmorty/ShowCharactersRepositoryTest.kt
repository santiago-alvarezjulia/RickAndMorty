package com.saj.rickandmorty

import com.google.common.truth.Truth
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.repositories.ShowCharactersRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class ShowCharactersRepositoryTest {

    private val rickAndMortyWebService = mockk<RickAndMortyWebService>()

    @Test
    fun `get show characters returns list of characters`() {
        val showCharacter = ShowCharacter()
        stubWebService(listOf(showCharacter))
        val charactersListRepository = ShowCharactersRepository(rickAndMortyWebService)
        val characters = charactersListRepository.fetchShowCharacters()
        Truth.assertThat(characters.isEmpty()).isFalse()
    }

    private fun stubWebService(characters: List<ShowCharacter>) {
        every { rickAndMortyWebService.getShowCharacters()} returns characters
    }
}