package com.saj.rickandmorty.network.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}