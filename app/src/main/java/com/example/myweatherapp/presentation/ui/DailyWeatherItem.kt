package com.example.myweatherapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.R
import com.example.myweatherapp.domain.weatherModel.DailyWeatherData
import com.example.myweatherapp.domain.weatherModel.WeatherType
import java.time.LocalDateTime

@Composable
fun DailyWeatherItem(
    dailyWeatherData: DailyWeatherData,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Start Text
        Text(
            text = dailyWeatherData.time.dayOfWeek.name.substring(0,3).lowercase().capitalize(Locale.current),
            color = textColor,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = modifier.weight(1f)
        )

        // Middle group: Icon + Text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_rain_icon),
                    modifier = Modifier.width(18.dp),
                    contentDescription = null
                )
                Text(
                    text = "${dailyWeatherData.precipitationProbability}%",
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Image(
                painter = painterResource(id = dailyWeatherData.weatherType.iconRes),
                contentDescription = null,
                modifier = Modifier.width(24.dp),
                alignment = Alignment.CenterEnd
            )
        }


        // End group: Icon + 2 Texts
        Text(
            text = "${dailyWeatherData.minTemperature}°C / ${dailyWeatherData.maxTemperature}°C",
            color = textColor,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = modifier.weight(2f)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomRow() {
    DailyWeatherItem(
        dailyWeatherData = DailyWeatherData(
            time = LocalDateTime.now(),
            maxTemperature = 34.2,
            minTemperature = 20.0,
            precipitationProbability = 0,
            weatherType = WeatherType.fromWMO(0)
        )
    )
}