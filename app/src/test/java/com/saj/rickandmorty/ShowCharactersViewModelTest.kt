package com.saj.rickandmorty

import com.google.common.truth.Truth.assertThat
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.repositories.ShowCharactersRepository
import com.saj.rickandmorty.viewmodels.ShowCharactersMasterViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class ShowCharactersViewModelTest {

    private val showCharactersRepo = mockk<ShowCharactersRepository>()

    @Test
    fun `get show characters returns empty list when no characters`() {
        stubFetchShowCharacters(emptyList())
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo)
        val characters = charactersListViewModel.getShowCharacters()
        assertThat(characters.isEmpty()).isTrue()
    }

    @Test
    fun `get show characters returns list of characters`() {
        val showCharacter = ShowCharacter()
        stubFetchShowCharacters(listOf(showCharacter))
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo)
        val characters = charactersListViewModel.getShowCharacters()
        assertThat(characters.isEmpty()).isFalse()
    }

    private fun stubFetchShowCharacters(characters: List<ShowCharacter>) {
        every { showCharactersRepo.fetchShowCharacters()} returns characters
    }
}