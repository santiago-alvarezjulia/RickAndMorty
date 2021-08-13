package com.saj.rickandmorty.network.mappers

import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import javax.inject.Inject

class ShowCharacterMapper @Inject constructor() : Mapper<ShowCharacterDTO, ShowCharacter> {
    override fun map(input: ShowCharacterDTO): ShowCharacter {
        return ShowCharacter(input.id, input.name, input.status, input.episodes.size)
    }
}