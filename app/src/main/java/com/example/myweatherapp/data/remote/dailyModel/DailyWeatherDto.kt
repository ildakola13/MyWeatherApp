package com.example.myweatherapp.data.remote.dailyModel

import com.squareup.moshi.Json

data class DailyWeatherDto(
    @field:Json(name = "daily")
    val weatherData: DailyWeatherDataDto
)
