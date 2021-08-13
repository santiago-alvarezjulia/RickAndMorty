package com.saj.rickandmorty

import com.google.common.truth.Truth.assertThat
import com.saj.rickandmorty.builders.ShowCharacterDTOBuilder
import com.saj.rickandmorty.network.mappers.ShowCharacterMapper
import org.junit.Test

class ShowCharacterDTOMapperTest {

    private val mapper =  ShowCharacterMapper()
    private val showCharacterDTO = ShowCharacterDTOBuilder().build()
    private val showCharacter = mapper.map(showCharacterDTO)

    @Test
    fun `show character mapping id`() {
        assertThat(showCharacter.id).isEqualTo(showCharacterDTO.id)
    }

    @Test
    fun `show character mapping name`() {
        assertThat(showCharacter.name).isEqualTo(showCharacterDTO.name)
    }

    @Test
    fun `show character mapping status`() {
        assertThat(showCharacter.status).isEqualTo(showCharacterDTO.status)
    }

    @Test
    fun `show character mapping episodes_count`() {
        assertThat(showCharacter.episodesCount).isEqualTo(showCharacterDTO.episodes.size)
    }

    @Test
    fun `show character mapping imageUrl`() {
        assertThat(showCharacter.imageUrl).isEqualTo(showCharacterDTO.imageUrl)
    }
}