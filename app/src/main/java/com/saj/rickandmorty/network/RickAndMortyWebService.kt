package com.saj.rickandmorty.network

import com.saj.rickandmorty.models.ShowCharacter
import retrofit2.http.GET

interface RickAndMortyWebService {
    @GET("character")
    fun getShowCharacters(): List<ShowCharacter>
}