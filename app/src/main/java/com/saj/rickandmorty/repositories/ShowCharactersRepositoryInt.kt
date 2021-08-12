package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharacter

interface ShowCharactersRepositoryInt {
    fun fetchShowCharacters(): List<ShowCharacter>
}