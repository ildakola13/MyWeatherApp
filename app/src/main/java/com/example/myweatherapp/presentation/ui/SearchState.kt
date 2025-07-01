package com.example.myweatherapp.presentation.ui

import com.example.myweatherapp.domain.locationModel.City

data class SearchState(
    val searchText: String = "",
    val citiesList: List<City> = listOf<City>(),
    val isSearching: Boolean = false,
)
