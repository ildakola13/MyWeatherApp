package com.example.myweatherapp.data.remote.dailyModel

import com.squareup.moshi.Json

data class DailyWeatherDataDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m_max")
    val maxTemperature: List<Double>,
    @field:Json(name = "temperature_2m_min")
    val minTemperature: List<Double>,
    @field:Json(name = "weathercode")
    val weatherCodes: List<Int>,
    @field:Json(name = "precipitation_probability_mean")
    val precipitationProbability: List<Int>
)
