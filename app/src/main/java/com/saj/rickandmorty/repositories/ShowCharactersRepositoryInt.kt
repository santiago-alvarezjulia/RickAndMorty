package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharactersPage
import com.saj.rickandmorty.network.responses.NetworkResponse

interface ShowCharactersRepositoryInt {
    suspend fun fetchNewShowCharactersPage(lastPage: ShowCharactersPage?): NetworkResponse<ShowCharactersPage>
}