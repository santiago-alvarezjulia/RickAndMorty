package com.saj.rickandmorty.models

data class ShowCharactersPage(val showCharacters: List<ShowCharacter>,
                              val nextPage: String?)