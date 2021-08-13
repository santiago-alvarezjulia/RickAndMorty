package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.saj.rickandmorty.network.mappers.ListMapper
import javax.inject.Inject

open class ShowCharactersRepository @Inject constructor(
    private val rickAndMortyWebService: RickAndMortyWebService,
    private val listMapper: ListMapper<ShowCharacterDTO, ShowCharacter>
) : ShowCharactersRepositoryInt {
    override suspend fun fetchShowCharacters(): List<ShowCharacter> {
        return listMapper.map(rickAndMortyWebService.getShowCharacters().showCharacters)
    }
}