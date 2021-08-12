package com.saj.rickandmorty.viewmodels

import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.repositories.ShowCharactersRepository

class ShowCharactersMasterViewModel(private val showCharactersRepository: ShowCharactersRepository) {
    fun getShowCharacters(): List<ShowCharacter> {
        return showCharactersRepository.fetchShowCharacters()
    }
}