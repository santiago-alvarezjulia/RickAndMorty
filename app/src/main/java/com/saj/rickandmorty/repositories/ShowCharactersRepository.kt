package com.saj.rickandmorty.repositories

import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.models.ShowCharactersPage
import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.saj.rickandmorty.network.mappers.ListMapper
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

open class ShowCharactersRepository @Inject constructor(
    private val rickAndMortyWebService: RickAndMortyWebService,
    private val listMapper: ListMapper<ShowCharacterDTO, ShowCharacter>
) : ShowCharactersRepositoryInt {
    override suspend fun fetchNewShowCharactersPage(lastPage: ShowCharactersPage?): ShowCharactersPage {
        val nextPage = lastPage?.nextPage
        val response = rickAndMortyWebService.getShowCharacters(nextPage)
        return ShowCharactersPage(
            listMapper.map(response.showCharacters),
            getNextPage(response.info.nextPage)
        )
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