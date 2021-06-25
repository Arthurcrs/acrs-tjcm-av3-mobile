package com.example.myweather.api

import CityWeather

data class WeatherResponse (
    val message: String,
    val count: Int,
    val list : List<CityWeather>
)