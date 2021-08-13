package com.saj.rickandmorty

import com.google.common.truth.Truth
import com.saj.rickandmorty.models.ShowCharacter
import org.junit.Test

class ShowCharacterTest {

    @Test
    fun `show character with diff id are not the same`() {
        val showCharacter = ShowCharacter(1, "Rick", "Dead", "image_url", 2)
        val showCharacterDiffId = ShowCharacter(2, "Rick", "Dead", "image_url", 2)
        Truth.assertThat(showCharacter.isTheSame(showCharacterDiffId)).isFalse()
    }

    @Test
    fun `show character with same id are the same`() {
        val id = 1
        val showCharacter = ShowCharacter(id, "Rick", "Dead", "image_url", 2)
        val showCharacterSameId = ShowCharacter(id, "Rick", "Dead", "image_url", 2)
        Truth.assertThat(showCharacter.isTheSame(showCharacterSameId)).isTrue()
    }

    @Test
    fun `same character with diff name have diff content`() {
        val id = 1
        val showCharacter = ShowCharacter(id, "Rick", "Dead", "image_url", 2)
        val showCharacterDiffName = ShowCharacter(id, "Rick2", "Dead", "image_url", 2)
        Truth.assertThat(showCharacter.isContentTheSame(showCharacterDiffName)).isFalse()
    }

    @Test
    fun `same character with diff imageUrl have diff content`() {
        val id = 1
        val showCharacter = ShowCharacter(id, "Rick", "Dead", "image_url", 2)
        val showCharacterDiffImageUrl = ShowCharacter(id, "Rick", "Dead", "image_url2", 2)
        Truth.assertThat(showCharacter.isContentTheSame(showCharacterDiffImageUrl)).isFalse()
    }

}