package com.saj.rickandmorty

import com.google.common.truth.Truth.assertThat
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.repositories.ShowCharactersRepository
import com.saj.rickandmorty.viewmodels.ShowCharactersMasterViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class ViewModelTest {

    private val showCharactersRepo = mockk<ShowCharactersRepository>()

    @Test
    fun `fetch characters returns empty list when no characters available`() {
        stubShowCharactersFetch(emptyList())
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo)
        val characters = charactersListViewModel.getShowCharacters()
        assertThat(characters.isEmpty()).isTrue()
    }

    @Test
    fun `fetch characters returns list of characters`() {
        val showCharacter = ShowCharacter()
        stubShowCharactersFetch(listOf(showCharacter))
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo)
        val characters = charactersListViewModel.getShowCharacters()
        assertThat(characters.isEmpty()).isFalse()
    }

    private fun stubShowCharactersFetch(characters: List<ShowCharacter>) {
        every { showCharactersRepo.fetchShowCharacters()} returns characters
    }
}