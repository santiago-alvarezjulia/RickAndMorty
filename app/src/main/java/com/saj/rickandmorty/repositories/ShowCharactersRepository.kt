package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharacter
import javax.inject.Inject

open class ShowCharactersRepository @Inject constructor() : ShowCharactersRepositoryInt {
    override fun fetchShowCharacters(): List<ShowCharacter> {
        return emptyList()
    }
}