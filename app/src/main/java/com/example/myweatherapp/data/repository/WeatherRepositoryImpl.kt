package com.example.myweatherapp.data.repository

import android.icu.util.TimeZone
import com.example.myweatherapp.data.mapper.toWeatherDataList
import com.example.myweatherapp.data.remote.Api
import com.example.myweatherapp.data.mapper.toWeatherInfo
import com.example.myweatherapp.domain.repository.WeatherRepository
import com.example.myweatherapp.domain.util.Resource
import com.example.myweatherapp.domain.weatherModel.DailyWeatherData
import com.example.myweatherapp.domain.weatherModel.WeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api
): WeatherRepository {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Resource<WeatherInfo> {
        return try {
            Resource.Success (
                data = api.getHourlyWeatherData(
                    hourly = "temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl",
                    latitude = latitude,
                    longitude = longitude,
                    timeZone = TimeZone.getDefault().id
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unexpected error happened.")
        }
    }

    suspend fun getDailyWeatherData(
        latitude: Double,
        longitude: Double
    ): Resource<List<DailyWeatherData>> {
        return try {
            Resource.Success(
                data = api.getDailyWeatherData(
                    daily = "temperature_2m_max,temperature_2m_min,precipitation_probability_mean,weathercode",
                    latitude = latitude,
                    longitude = longitude,
                    timeZone = TimeZone.getDefault().id
                ).toWeatherDataList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unexpected error happened.")
        }
    }

    override fun getWeatherDataFlow(
        lat: Double,
        lon: Double
    ): Flow<Resource<WeatherInfo>>  = flow {
        emit(getWeatherData(lat, lon))
    }
    override fun getDailyWeatherDataFlow(
        lat: Double,
        lon: Double
    ): Flow<Resource<List<DailyWeatherData>>> = flow {
        emit(getDailyWeatherData(lat, lon))
    }

}