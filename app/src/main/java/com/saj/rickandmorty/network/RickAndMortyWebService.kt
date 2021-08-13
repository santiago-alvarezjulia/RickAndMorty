package com.saj.rickandmorty.network

import com.saj.rickandmorty.network.responses.GetCharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyWebService {
    @GET("character")
    suspend fun getShowCharacters(@Query("page") page: String?): GetCharactersResponse
}