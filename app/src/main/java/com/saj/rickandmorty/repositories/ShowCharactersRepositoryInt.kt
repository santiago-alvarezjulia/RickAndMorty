package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharacter

interface ShowCharactersRepositoryInt {
    suspend fun fetchShowCharacters(): List<ShowCharacter>
}