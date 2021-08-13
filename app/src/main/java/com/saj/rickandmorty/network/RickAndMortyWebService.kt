package com.saj.rickandmorty.network

import com.saj.rickandmorty.network.responses.GetCharactersResponse
import retrofit2.http.GET

interface RickAndMortyWebService {
    @GET("character")
    suspend fun getShowCharacters(): GetCharactersResponse
}