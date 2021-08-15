package com.saj.rickandmorty.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowCharacter(
    val id: Int, val name: String, val status: String, val imageUrl: String,
    val episodesCount: Int, val species: String
) : Parcelable {

    fun isTheSame(other: ShowCharacter): Boolean {
        return this.id == other.id
    }

    fun isContentTheSame(other: ShowCharacter): Boolean {
        return this.name == other.name &&
                this.imageUrl == other.imageUrl &&
                this.species == other.species
    }
}