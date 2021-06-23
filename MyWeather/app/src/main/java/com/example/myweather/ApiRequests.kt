package com.example.myweather

import Json4Kotlin_Base
import Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {

    @GET("weather")
    fun getCityWeather(
        @Query("q") city: String,
        @Query("appid") API_KEY: String,
        @Query("lang") lang: String,
        @Query("units") units: String
    ): Call<Json4Kotlin_Base>
}