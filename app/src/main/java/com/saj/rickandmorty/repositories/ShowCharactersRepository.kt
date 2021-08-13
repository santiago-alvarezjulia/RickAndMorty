package com.saj.rickandmorty.repositories

import android.net.Uri
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.models.ShowCharactersPage
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.saj.rickandmorty.network.mappers.ListMapper
import javax.inject.Inject

open class ShowCharactersRepository @Inject constructor(
    private val rickAndMortyWebService: RickAndMortyWebService,
    private val listMapper: ListMapper<ShowCharacterDTO, ShowCharacter>
) : ShowCharactersRepositoryInt {
    override suspend fun fetchNewShowCharactersPage(): ShowCharactersPage {
        val response = rickAndMortyWebService.getShowCharacters()
        return ShowCharactersPage(
            listMapper.map(response.showCharacters),
            getNextPageQueryParams(response.info.nextPage)
        )
    }

    private fun getNextPageQueryParams(nextPageUrl: String): HashMap<String, String> {
        val uri = Uri.parse(nextPageUrl)
        val queryParams = hashMapOf<String, String>()
        for (key in uri.queryParameterNames) {
            queryParams[key] = uri.getQueryParameter(key)!!
        }
        return queryParams
    }
}