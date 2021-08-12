package com.saj.rickandmorty

import com.google.common.truth.Truth
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.repositories.ShowCharactersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ShowCharactersRepositoryTest {

    private val rickAndMortyWebService = mockk<RickAndMortyWebService>()

    @ExperimentalCoroutinesApi
    @Test
    fun `get show characters returns list of characters`() = runBlockingTest {
        val showCharacter = ShowCharacter()
        stubWebService(listOf(showCharacter))
        val charactersListRepository = ShowCharactersRepository(rickAndMortyWebService)
        val characters = charactersListRepository.fetchShowCharacters()
        Truth.assertThat(characters.isEmpty()).isFalse()
    }

    private fun stubWebService(characters: List<ShowCharacter>) {
        coEvery { rickAndMortyWebService.getShowCharacters()} returns characters
    }
}