package com.saj.rickandmorty.builders

import com.saj.rickandmorty.network.dtos.InfoDTO

class InfoDTOBuilder {

    private var nextPage : String? = "https://rickandmortyapi.com/api/character/?page=2"

    fun setNextPage(newNextPage: String?) : InfoDTOBuilder {
        this.nextPage = newNextPage
        return this
    }

    fun build() : InfoDTO {
        return InfoDTO(nextPage)
    }
}