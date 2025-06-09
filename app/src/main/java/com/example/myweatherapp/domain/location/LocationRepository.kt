package com.example.myweatherapp.domain.location

import android.location.Location
import com.example.myweatherapp.domain.locationModel.City

interface LocationRepository {
    suspend fun getCurrentLocation(): Location?
    suspend fun getCityNameFromGeocoder(latitude: Double, longitude: Double): City?
    suspend fun searchCity(text: String): List<City>
}