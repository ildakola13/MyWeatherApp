package com.example.myweatherapp.data.remote

import com.example.myweatherapp.data.remote.dailyModel.DailyWeatherDto
import com.example.myweatherapp.data.remote.hourlyModel.WeatherDto
import com.example.myweatherapp.data.remote.locationModel.CityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("v1/forecast")
    suspend fun getHourlyWeatherData(
        @Query("hourly") hourly: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("timezone") timeZone: String
    ): WeatherDto

    @GET("v1/forecast")
    suspend fun getDailyWeatherData(
        @Query("daily") daily: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("timezone") timeZone: String
    ): DailyWeatherDto

    @GET("https://geocoding-api.open-meteo.com/v1/search")
    suspend fun searchLocation(
        @Query("name") name: String,
        @Query("count") count: Int
    ): CityDto
}