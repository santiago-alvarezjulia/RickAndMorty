package com.saj.rickandmorty.network.responses

sealed class NetworkResponse<out T: Any> {
    data class Success<T : Any>(val body: T) : NetworkResponse<T>()
    data class Error(val message: String) : NetworkResponse<Nothing>()
}
