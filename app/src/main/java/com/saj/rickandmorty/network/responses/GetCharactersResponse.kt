package com.saj.rickandmorty.network.responses

import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCharactersResponse(@Json(name = "results") val showCharacters: List<ShowCharacterDTO>)