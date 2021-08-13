package com.saj.rickandmorty.models

data class ShowCharacter(val id: Int, val name: String, val status: String, val imageUrl: String,
                         val episodesCount: Int) {

    fun isTheSame(other: ShowCharacter): Boolean {
        return this.id == other.id
    }

    fun isContentTheSame(other: ShowCharacter): Boolean {
        return this.name == other.name &&
                this.imageUrl == other.imageUrl
    }
}