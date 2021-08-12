package com.saj.rickandmorty.di

import com.saj.rickandmorty.repositories.ShowCharactersRepository
import com.saj.rickandmorty.repositories.ShowCharactersRepositoryInt
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ShowCharactersViewModelModule {

    @Binds
    abstract fun bindShowCharactersRepository(
        showCharactersRepository: ShowCharactersRepository
    ): ShowCharactersRepositoryInt
}