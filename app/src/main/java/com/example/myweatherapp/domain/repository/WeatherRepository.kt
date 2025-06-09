package com.example.myweatherapp.domain.repository

import com.example.myweatherapp.domain.util.Resource
import com.example.myweatherapp.domain.weatherModel.DailyWeatherData
import com.example.myweatherapp.domain.weatherModel.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherDataFlow(lat: Double, lon: Double): Flow<Resource<WeatherInfo>>
    fun getDailyWeatherDataFlow(lat: Double, lon: Double): Flow<Resource<List<DailyWeatherData>>>
}