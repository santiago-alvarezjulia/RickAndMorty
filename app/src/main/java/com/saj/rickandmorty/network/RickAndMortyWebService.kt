package com.saj.rickandmorty.network

import com.saj.rickandmorty.network.responses.GetCharactersResponse
import com.saj.rickandmorty.network.responses.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyWebService {
    @GET("character")
    suspend fun getShowCharacters(@Query("page") page: String?): NetworkResponse<GetCharactersResponse>
}