package com.saj.rickandmorty.network.mappers

import javax.inject.Inject

class ListMapper<I, O> @Inject constructor(
    private val mapper: Mapper<I, O>
) {
    fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}