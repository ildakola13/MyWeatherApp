# MyWeatherApp 🌤️

A modern Android weather app built with **Kotlin**, **Jetpack Compose**, **Coroutines & Flow**, and clean architecture principles. It fetches real-time weather data for the user’s current location or searched cities, offering a responsive and intuitive UI.

---

## 🧱 Features

- **Live Weather**: Displays current conditions (temperature, humidity, wind, etc.)
- **Search Locations**: Search by city name with real-time suggestions and debounced input
- **Hourly & Daily Forecasts**: Shows weather predictions for the next hours and week
- **Automatic Geocoding**: Retrieves city names from GPS coordinates using Android Geocoder & Google Geocoding API fallback
- **Reactive UI**: Powered by Jetpack Compose and `StateFlow`

---

## 🚀 Tech Stack

| Layer            | Tools & Libraries |
|------------------|-------------------|
| **UI**           | Jetpack Compose |
| **State Management** | Kotlin Coroutines & StateFlow |
| **Networking**   | Retrofit + GSON |
| **Location**     | Android Geocoder (API 33+) & Google Geocoding API |
| **Dependency Injection** | Hilt |
| **Architecture** | MVVM |
| **Other**        | Shared `WeatherState`, Location permissions handling |

---

