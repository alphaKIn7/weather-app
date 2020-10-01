package com.example.weatherapp2.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    val description: String?,
    val icon: String?,
    val id: Int?,
    val main: String?
)