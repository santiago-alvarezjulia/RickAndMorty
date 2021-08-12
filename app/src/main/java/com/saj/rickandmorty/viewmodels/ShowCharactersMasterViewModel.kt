package com.saj.rickandmorty.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.repositories.ShowCharactersRepositoryInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowCharactersMasterViewModel @Inject constructor(
        private val showCharactersRepository: ShowCharactersRepositoryInt
    ): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getShowCharacters()
        }
    }

    suspend fun getShowCharacters(): List<ShowCharacter> {
        return showCharactersRepository.fetchShowCharacters()
    }
}