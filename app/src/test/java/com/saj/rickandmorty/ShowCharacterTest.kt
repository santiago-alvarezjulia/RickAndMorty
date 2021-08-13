package com.saj.rickandmorty

import com.google.common.truth.Truth
import com.saj.rickandmorty.models.ShowCharacter
import org.junit.Test

class ShowCharacterTest {

    @Test
    fun `show character with diff id are not the same`() {
        val showCharacter = ShowCharacter(1, "Rick", "Dead", 2)
        val showCharacterDiffId = ShowCharacter(2, "Rick", "Dead", 2)
        Truth.assertThat(showCharacter.isTheSame(showCharacterDiffId)).isFalse()
    }

    @Test
    fun `show character with same id are the same`() {
        val id = 1
        val showCharacter = ShowCharacter(id, "Rick", "Dead", 2)
        val showCharacterSameId = ShowCharacter(id, "Rick", "Dead", 2)
        Truth.assertThat(showCharacter.isTheSame(showCharacterSameId)).isTrue()
    }

    @Test
    fun `same character with diff name have diff content`() {
        val id = 1
        val showCharacter = ShowCharacter(id, "Rick", "Dead", 2)
        val showCharacterSameId = ShowCharacter(id, "Rick2", "Dead", 2)
        Truth.assertThat(showCharacter.isContentTheSame(showCharacterSameId)).isFalse()
    }

}