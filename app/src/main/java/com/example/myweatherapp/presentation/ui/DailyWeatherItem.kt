package com.example.myweatherapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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
import kotlin.math.roundToInt

@Composable
fun DailyWeatherItem(
    modifier: Modifier = Modifier,
    dailyWeatherData: DailyWeatherData,
    style: Style = Style()) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Start Text
        Text(
            text = dailyWeatherData.time.dayOfWeek.name.lowercase().capitalize(Locale.current),
            color = style.textColor,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = modifier.weight(1f)
        )
        Spacer(modifier = modifier.size(8.dp))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_drop),
            modifier = Modifier.size(18.dp),
            tint = style.iconTint,
            contentDescription = null
        )

        Text(
            text = "${dailyWeatherData.precipitationProbability}%",
            color = style.textColor,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.size(8.dp))
        Image(
            painter = painterResource(id = dailyWeatherData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.width(24.dp),
            alignment = Alignment.CenterEnd
        )
        Spacer(modifier = modifier.size(16.dp))
        // End group: Icon + 2 Texts
        Text(
            text = "${dailyWeatherData.minTemperature.roundToInt()}°C / ${dailyWeatherData.maxTemperature.roundToInt()}°C",
            color = style.textColor,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
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