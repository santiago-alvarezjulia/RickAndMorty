package com.saj.rickandmorty.network.dtos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoDTO(@Json(name = "next") val nextPage: String?)
