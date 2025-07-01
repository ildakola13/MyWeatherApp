package com.example.myweatherapp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DailyForecast(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    state.dailyWeather?.let { weatherData ->
        Card(
            colors = CardColors(
                containerColor = backgroundColor,
                contentColor = backgroundColor,
                disabledContentColor = backgroundColor,
                disabledContainerColor = backgroundColor
            ),
            shape = RoundedCornerShape(10.dp),
        )  {
            LazyColumn(
                modifier = modifier.fillMaxWidth()
                    .padding(10.dp)
                    .heightIn(max = 800.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                content = {
                    items(items = weatherData) {data ->
                        DailyWeatherItem(dailyWeatherData = data)
                    }
                }
            )
        }
    }

}