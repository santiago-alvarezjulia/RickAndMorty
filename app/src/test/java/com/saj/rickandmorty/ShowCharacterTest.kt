package com.saj.rickandmorty

import com.google.common.truth.Truth
import com.saj.rickandmorty.builders.ShowCharacterBuilder
import org.junit.Test

class ShowCharacterTest {

    @Test
    fun `show character with diff id are not the same`() {
        val showCharacter = ShowCharacterBuilder().setId(1).build()
        val showCharacterDiffId = ShowCharacterBuilder().setId(2).build()
        Truth.assertThat(showCharacter.isTheSame(showCharacterDiffId)).isFalse()
    }

    @Test
    fun `show character with same id are the same`() {
        val id = 1
        val showCharacter = ShowCharacterBuilder().setId(id).build()
        val showCharacterSameId = ShowCharacterBuilder().setId(id).build()
        Truth.assertThat(showCharacter.isTheSame(showCharacterSameId)).isTrue()
    }

    @Test
    fun `same character with diff name have diff content`() {
        val id = 1
        val showCharacter = ShowCharacterBuilder().setId(id).setName("Rick").build()
        val showCharacterDiffName = ShowCharacterBuilder().setId(id).setName("Rick2").build()
        Truth.assertThat(showCharacter.isContentTheSame(showCharacterDiffName)).isFalse()
    }

    @Test
    fun `same character with diff imageUrl have diff content`() {
        val id = 1
        val showCharacter = ShowCharacterBuilder().setId(id).setImageUrl("image_url").build()
        val showCharacterDiffImageUrl = ShowCharacterBuilder().setId(id).setImageUrl("image_url2").build()
        Truth.assertThat(showCharacter.isContentTheSame(showCharacterDiffImageUrl)).isFalse()
    }

    @Test
    fun `same character with diff species have diff content`() {
        val id = 1
        val showCharacter = ShowCharacterBuilder().setId(id).setSpecies("Human").build()
        val showCharacterDiffSpecies = ShowCharacterBuilder().setId(id).setSpecies("Human2").build()
        Truth.assertThat(showCharacter.isContentTheSame(showCharacterDiffSpecies)).isFalse()
    }

}