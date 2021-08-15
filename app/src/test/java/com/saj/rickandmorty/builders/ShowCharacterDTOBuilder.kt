package com.saj.rickandmorty.builders

import com.saj.rickandmorty.network.dtos.ShowCharacterDTO

class ShowCharacterDTOBuilder {

    private var id = 1
    private var name = "Rick Sanchez"
    private var status = "Alive"
    private var imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    private var episodes = listOf("E1, E2")
    private var species = "Human"

    fun setId(newId: Int) : ShowCharacterDTOBuilder {
        this.id = newId
        return this
    }

    fun setName(newName: String) : ShowCharacterDTOBuilder {
        this.name = newName
        return this
    }

    fun setStatus(newStatus: String) : ShowCharacterDTOBuilder {
        this.status = newStatus
        return this
    }

    fun setImageUrl(newImageUrl: String) : ShowCharacterDTOBuilder {
        this.imageUrl = newImageUrl
        return this
    }

    fun setEpisodes(newEpisodes: List<String>) : ShowCharacterDTOBuilder {
        this.episodes = newEpisodes
        return this
    }

    fun setSpecies(newSpecie: String) : ShowCharacterDTOBuilder {
        this.species = newSpecie
        return this
    }

    fun build() : ShowCharacterDTO {
        return ShowCharacterDTO(id, name, status, imageUrl, episodes, species)
    }
}