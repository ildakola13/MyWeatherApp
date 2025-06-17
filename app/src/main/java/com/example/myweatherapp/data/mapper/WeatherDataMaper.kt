package com.example.myweatherapp.data.mapper

import com.example.myweatherapp.data.remote.dailyModel.DailyWeatherDto
import com.example.myweatherapp.data.remote.hourlyModel.HourlyWeatherDataDto
import com.example.myweatherapp.data.remote.hourlyModel.WeatherDto
import com.example.myweatherapp.domain.weatherModel.DailyWeatherData
import com.example.myweatherapp.domain.weatherModel.HourlyWeatherData
import com.example.myweatherapp.domain.weatherModel.WeatherInfo
import com.example.myweatherapp.domain.weatherModel.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun HourlyWeatherDataDto.toWeatherDataList(): List<HourlyWeatherData> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        HourlyWeatherData(
            time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
            temperatureCelsius = temperature,
            pressure = pressure,
            windSpeed = windSpeed,
            humidity = humidity,
            weatherType = WeatherType.fromWMO(weatherCode)
        )
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataList = hourlyWeatherData.toWeatherDataList()
    val now = LocalDateTime.now()
    val last24hData = weatherDataList.filter {
        (it.time.dayOfMonth == now.dayOfMonth && it.time.hour >= now.hour) || (it.time.dayOfMonth == now.dayOfMonth + 1 && it.time.hour < now.hour)
    }
    val currentWeatherData = last24hData.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        currentWeatherData = currentWeatherData,
        last24HWeatherData = last24hData
        )
}

fun DailyWeatherDto.toWeatherDataList(): List<DailyWeatherData> {
    return weatherData.run {
        time.mapIndexed { index, time ->
            val minTemp = minTemperature[index]
            val maxTemp = maxTemperature[index]
            val weatherCode = weatherCodes[index]
            val precipitation = precipitationProbability[index]
            DailyWeatherData(
                time = LocalDate.parse(time, DateTimeFormatter.ISO_DATE).atStartOfDay(),
                minTemperature = minTemp,
                maxTemperature = maxTemp,
                weatherType = WeatherType.fromWMO(weatherCode),
                precipitationProbability = precipitation
            )
        }
    }


}
