package com.example.myweather

import CityWeather
import com.example.myweather.api.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {

    @GET("find")
    fun getCityWeather(
        @Query("q") city: String,
        @Query("appid") API_KEY: String,
        @Query("lang") lang: String,
        @Query("units") units: String
    ): Call<WeatherResponse>
}