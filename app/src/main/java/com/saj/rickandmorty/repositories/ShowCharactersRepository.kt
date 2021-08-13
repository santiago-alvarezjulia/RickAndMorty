package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.models.ShowCharactersPage
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.saj.rickandmorty.network.mappers.ListMapper
import java.net.URL
import javax.inject.Inject

open class ShowCharactersRepository @Inject constructor(
    private val rickAndMortyWebService: RickAndMortyWebService,
    private val listMapper: ListMapper<ShowCharacterDTO, ShowCharacter>
) : ShowCharactersRepositoryInt {
    override suspend fun fetchNewShowCharactersPage(lastPage: ShowCharactersPage?): ShowCharactersPage {
        val nextPage = if (lastPage == null) {
            null
        } else {
            lastPage.nextPageQueryParams!!["page"]
        }
        val response = rickAndMortyWebService.getShowCharacters(nextPage)
        return ShowCharactersPage(
            listMapper.map(response.showCharacters),
            getNextPageQueryParams(response.info.nextPage)
        )
    }

    private fun getNextPageQueryParams(nextPageUrl: String): HashMap<String, String> {
        val url = URL(nextPageUrl)
        val queryParams = hashMapOf<String, String>()
        val querySplitted = url.query.split('&')
        for (queryParam in querySplitted) {
            val keyValue = queryParam.split('=')
            queryParams[keyValue[0]] = keyValue[1]
        }
        return queryParams
    }
}