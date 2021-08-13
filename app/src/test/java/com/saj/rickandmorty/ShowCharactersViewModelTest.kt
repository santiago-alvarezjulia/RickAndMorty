package com.saj.rickandmorty

import com.google.common.truth.Truth.assertThat
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.repositories.ShowCharactersRepository
import com.saj.rickandmorty.viewmodels.ShowCharactersMasterViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ShowCharactersViewModelTest {

    private val showCharactersRepo = mockk<ShowCharactersRepository>()

    @ExperimentalCoroutinesApi
    @Test
    fun `get show characters returns empty list when no characters`() = runBlockingTest {
        stubFetchShowCharacters(emptyList())
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo)
        val characters = charactersListViewModel.getShowCharacters()
        assertThat(characters.isEmpty()).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get show characters returns list of characters`() = runBlockingTest {
        val showCharacter = ShowCharacter(1, "Rick Sanchez", "Dead",
            "image_url", 2)
        stubFetchShowCharacters(listOf(showCharacter))
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo)
        val characters = charactersListViewModel.getShowCharacters()
        assertThat(characters.isEmpty()).isFalse()
    }

    private fun stubFetchShowCharacters(characters: List<ShowCharacter>) {
        coEvery { showCharactersRepo.fetchShowCharacters()} returns characters
    }
}