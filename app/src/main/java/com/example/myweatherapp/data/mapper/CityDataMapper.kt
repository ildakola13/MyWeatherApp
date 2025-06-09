package com.example.myweatherapp.data.mapper

import com.example.myweatherapp.data.remote.locationModel.CityDto
import com.example.myweatherapp.domain.locationModel.City

fun CityDto.toCityList(): List<City> {
    return results
}