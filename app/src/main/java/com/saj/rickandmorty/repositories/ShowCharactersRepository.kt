package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.models.ShowCharactersPage
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.saj.rickandmorty.network.mappers.ListMapper
import com.saj.rickandmorty.network.responses.NetworkResponse
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

open class ShowCharactersRepository @Inject constructor(
    private val rickAndMortyWebService: RickAndMortyWebService,
    private val listMapper: ListMapper<ShowCharacterDTO, ShowCharacter>
) : ShowCharactersRepositoryInt {

    override suspend fun fetchNewShowCharactersPage(lastPage: ShowCharactersPage?): NetworkResponse<ShowCharactersPage> {
        val nextPage = lastPage?.nextPage
        return when (val response = rickAndMortyWebService.getShowCharacters(nextPage)) {
            is NetworkResponse.Success -> NetworkResponse.Success(ShowCharactersPage(
                listMapper.map(response.body.showCharacters),
                getNextPage(response.body.info.nextPage)
            ))
            is NetworkResponse.Error -> NetworkResponse.Error(response.message)
        }
    }

    private fun getNextPage(nextPageUrl: String?): String? {
        nextPageUrl?.let {
            return try {
                val url = URL(nextPageUrl)
                val querySplit = url.query.split('&')
                val keyValue = querySplit[0].split('=')
                keyValue[1]
            } catch (e: MalformedURLException) {
                null
            }
        } ?: run {
            return null
        }
    }
}