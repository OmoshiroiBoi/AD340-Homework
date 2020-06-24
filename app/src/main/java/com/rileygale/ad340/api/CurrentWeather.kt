package com.rileygale.ad340.api

import com.squareup.moshi.Json

//define the models needed to model the api response from the weather map api

data class Forecast(val temp: Float)
data class Coordinates(val lat: Float, val lon: Float)

data class CurrentWeather(
    val name: String,
    val coord: Coordinates,
    @field:Json(name = "main") val forecast: Forecast
    )
