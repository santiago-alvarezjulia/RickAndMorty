package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.network.RickAndMortyWebService
import javax.inject.Inject

open class ShowCharactersRepository @Inject constructor(
    private val rickAndMortyWebService: RickAndMortyWebService
) : ShowCharactersRepositoryInt {
    override suspend fun fetchShowCharacters(): List<ShowCharacter> {
        return rickAndMortyWebService.getShowCharacters()
    }
}