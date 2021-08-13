package com.saj.rickandmorty.testUtils

import com.saj.rickandmorty.models.ShowCharacter

class ShowCharacterBuilder {

    private var id = 1
    private var name = "Rick Sanchez"
    private var status = "Alive"
    private var imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    private var episodesCount = 20

    fun setId(newId: Int) : ShowCharacterBuilder {
        this.id = newId
        return this
    }

    fun setName(newName: String) : ShowCharacterBuilder {
        this.name = newName
        return this
    }

    fun setImageUrl(newImageUrl: String) : ShowCharacterBuilder {
        this.imageUrl = newImageUrl
        return this
    }

    fun build() : ShowCharacter {
        return ShowCharacter(id, name, status, imageUrl, episodesCount)
    }
}