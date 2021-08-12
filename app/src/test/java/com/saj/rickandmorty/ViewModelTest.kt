package com.saj.rickandmorty

import com.google.common.truth.Truth.assertThat
import com.saj.rickandmorty.viewmodels.CharactersListViewModel
import org.junit.Test

class ViewModelTest {

    @Test
    fun `fetch characters returns emptyList`() {
        val charactersListViewModel = CharactersListViewModel()
        val characters = charactersListViewModel.fetchCharacters()
        assertThat(characters.isEmpty()).isTrue()
    }
}