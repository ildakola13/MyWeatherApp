package com.example.myweatherapp.presentation.ui

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.domain.location.LocationRepository
import com.example.myweatherapp.domain.locationModel.City
import com.example.myweatherapp.domain.repository.WeatherRepository
import com.example.myweatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {


    private val _locationFlow = MutableStateFlow<Location?>(null)

    private val _currentLocation = MutableStateFlow<City>(City())
    private val _locationCity = MutableStateFlow<City>(City())
    val locationCity: StateFlow<City> = _locationCity.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _isSearchingLocation = MutableStateFlow(false)

    private val _isSearching = MutableStateFlow(false)

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _cities = MutableStateFlow(listOf<City>())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<WeatherState> = combine(
        _locationFlow.filterNotNull(),
        _isSearchingLocation
    ) { location, isSearching ->
        combine(
            weatherRepository.getWeatherDataFlow(location.latitude, location.longitude),
            weatherRepository.getDailyWeatherDataFlow(location.latitude, location.longitude)
        ) { weatherResult, dailyResult ->
            val weatherInfo = (weatherResult as? Resource.Success)?.data
            val dailyWeather = (dailyResult as? Resource.Success)?.data
            val error = listOfNotNull(
                (weatherResult as? Resource.Error)?.message,
                (dailyResult as? Resource.Error)?.message
            ).firstOrNull()

            WeatherState(
                weatherInfo = weatherInfo,
                dailyWeather = dailyWeather,
                isLoading = false,
                isSearching = isSearching,
                error = error
            )
        }
    }.flattenMerge()
        .onStart {
            _isLoading.value = true
        }
        .catch { e ->
            _error.value = e.message
            emit(WeatherState(error = e.message))
        }
        .onEach {
            _isLoading.value = false
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WeatherState())


    fun loadWeatherInfo() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val location = locationRepository.getCurrentLocation()
            if (location != null) {
                _locationFlow.value = location

                // Get city name
                locationRepository.getCityNameFromGeocoder(location.latitude, location.longitude)?.let {
                    setLocationCity(it)
                    _currentLocation.value = it
                }
            } else {
                _error.value = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
            }
        }
    }

    fun setSearching(value: Boolean) {
        _isSearchingLocation.value = value
    }

    fun setLocationCity(city: City) {
        _locationCity.value = city
    }

    fun selectCurrentLocation() {
        if (_currentLocation.value == _locationCity.value) {
            setSearching(false)
        } else {
            setLocationCity(_currentLocation.value)
        }
    }

    fun selectNewLocation(city: City) {
        _locationFlow.value = Location("customProvider").apply {
            city.latitude?.let { latitude = it }
            city.longitude?.let { longitude = it }
        }
        setSearching(false)
        setLocationCity(city)
    }

    fun selectLocation(city: City? = null) {
        city?.let {
            selectNewLocation(city)
        } ?: {
            selectCurrentLocation()
        }
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    val cities = searchText
        .debounce(1000L)
        .distinctUntilChanged()
        .filter { it.isNotBlank() }
        .flatMapLatest { text ->
            if (text.length < 3) {
                flowOf(_cities.value) // or emptyList()
            } else {
                flow {
                    _isSearching.update { true }
                    val result = locationRepository.searchCity(text)
                    emit(result)
                    _isSearching.update { false }
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _cities.value
        )

    val searchState: StateFlow<SearchState> = combine(
        _searchText,
        cities,
        _isSearching
    ) { searchText, cities, isSearching ->
        SearchState(
            searchText,
            cities,
            isSearching
        )
    }.stateIn(viewModelScope,  SharingStarted.WhileSubscribed(5000), SearchState())

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}