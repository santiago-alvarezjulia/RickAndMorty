package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharactersPage

interface ShowCharactersRepositoryInt {
    suspend fun fetchNewShowCharactersPage(lastPage: ShowCharactersPage?): ShowCharactersPage
}