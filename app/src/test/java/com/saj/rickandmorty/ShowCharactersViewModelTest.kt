package com.saj.rickandmorty

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.models.ShowCharactersPage
import com.saj.rickandmorty.repositories.ShowCharactersRepository
import com.saj.rickandmorty.testUtils.MainCoroutineRule
import com.saj.rickandmorty.testUtils.ShowCharacterBuilder
import com.saj.rickandmorty.testUtils.runBlockingTest
import com.saj.rickandmorty.viewmodels.ShowCharactersMasterViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShowCharactersViewModelTest {

    private val showCharactersRepo = mockk<ShowCharactersRepository>()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `get show characters returns empty list when no characters`() = coroutineRule.runBlockingTest {
        stubFetchShowCharacters(emptyList())
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo,
            coroutineRule.testDispatcher)
        charactersListViewModel.loadNewShowCharactersPage()
        assertThat(charactersListViewModel.showCharacterLiveData.value?.isEmpty()).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get show characters returns list of characters`() = coroutineRule.runBlockingTest {
        val showCharacter = ShowCharacterBuilder().build()
        stubFetchShowCharacters(listOf(showCharacter))
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo,
            coroutineRule.testDispatcher)
        charactersListViewModel.loadNewShowCharactersPage()
        assertThat(charactersListViewModel.showCharacterLiveData.value?.isEmpty()).isFalse()
    }

    private fun stubFetchShowCharacters(characters: List<ShowCharacter>) {
        val newPage = ShowCharactersPage(characters, hashMapOf())
        coEvery { showCharactersRepo.fetchNewShowCharactersPage(any())} returns newPage
    }
}