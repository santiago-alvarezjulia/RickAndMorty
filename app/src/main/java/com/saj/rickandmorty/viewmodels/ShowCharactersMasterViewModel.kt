package com.saj.rickandmorty.viewmodels

import androidx.lifecycle.ViewModel
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.repositories.ShowCharactersRepositoryInt
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowCharactersMasterViewModel @Inject constructor(
        private val showCharactersRepository: ShowCharactersRepositoryInt
    ): ViewModel() {
    fun getShowCharacters(): List<ShowCharacter> {
        return showCharactersRepository.fetchShowCharacters()
    }
}