package com.saj.rickandmorty.di

import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.network.dtos.ShowCharacterDTO
import com.saj.rickandmorty.network.mappers.Mapper
import com.saj.rickandmorty.network.mappers.ShowCharacterMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ShowCharactersRepositoryModule {

    @Binds
    abstract fun bindShowCharacterListMapper2(
        showCharactersMapper: ShowCharacterMapper
    ): Mapper<ShowCharacterDTO, ShowCharacter>
}