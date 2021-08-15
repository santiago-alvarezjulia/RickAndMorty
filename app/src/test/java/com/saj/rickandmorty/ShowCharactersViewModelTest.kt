package com.saj.rickandmorty

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.saj.rickandmorty.builders.ShowCharacterBuilder
import com.saj.rickandmorty.idlingResources.EspressoCountingIdlingResource
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.models.ShowCharactersPage
import com.saj.rickandmorty.network.responses.NetworkResponse
import com.saj.rickandmorty.repositories.ShowCharactersRepository
import com.saj.rickandmorty.testUtils.MainCoroutineRule
import com.saj.rickandmorty.testUtils.runBlockingTest
import com.saj.rickandmorty.viewmodels.ShowCharactersMasterViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShowCharactersViewModelTest {

    private val showCharactersRepo = mockk<ShowCharactersRepository>()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun mockEspressoCountingIdlingResource() {
        mockkObject(EspressoCountingIdlingResource)
        every { EspressoCountingIdlingResource.processStarts() } returns Unit
        every { EspressoCountingIdlingResource.processEnds() } returns Unit
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get show characters returns empty list when no characters`() = coroutineRule.runBlockingTest {
        stubFetchShowCharacters(emptyList(), "1")
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo,
            coroutineRule.testDispatcher)
        assertThat(charactersListViewModel.showCharacterLiveData.value?.isEmpty()).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get show characters returns list of characters`() = coroutineRule.runBlockingTest {
        val showCharacter = ShowCharacterBuilder().build()
        stubFetchShowCharacters(listOf(showCharacter), "1")
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo,
            coroutineRule.testDispatcher)
        assertThat(charactersListViewModel.showCharacterLiveData.value?.isEmpty()).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `consecutive loadNewShowCharacter should accumulate characters in livedata`() = coroutineRule.runBlockingTest {
        val showCharacter = ShowCharacterBuilder().build()
        stubFetchShowCharacters(listOf(showCharacter), "1")
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo,
            coroutineRule.testDispatcher)

        val showCharacter2 = ShowCharacterBuilder().setId(120).build()
        stubFetchShowCharacters(listOf(showCharacter2), "2")
        charactersListViewModel.loadNewShowCharactersPage()

        assertThat(charactersListViewModel.showCharacterLiveData.value?.size).isEqualTo(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when network response is error, post live data event`() = coroutineRule.runBlockingTest {
        val errorMsg = "error msg"
        stubFetchShowCharactersReturnsError(errorMsg)
        val charactersListViewModel = ShowCharactersMasterViewModel(showCharactersRepo,
            coroutineRule.testDispatcher)

        assertThat(charactersListViewModel.loadPageErrorLiveData.value?.peekContent()).isEqualTo(errorMsg)
    }

    private fun stubFetchShowCharacters(characters: List<ShowCharacter>, nextPage: String) {
        val newPage = ShowCharactersPage(characters, nextPage)
        coEvery { showCharactersRepo.fetchNewShowCharactersPage(any())} returns NetworkResponse.Success(newPage)
    }

    private fun stubFetchShowCharactersReturnsError(errorMsg: String) {
        coEvery { showCharactersRepo.fetchNewShowCharactersPage(any())} returns NetworkResponse.Error(errorMsg)
    }
}