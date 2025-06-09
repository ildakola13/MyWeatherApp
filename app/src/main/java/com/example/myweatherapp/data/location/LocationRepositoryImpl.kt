package com.example.myweatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.myweatherapp.data.mapper.toCityList
import com.example.myweatherapp.data.remote.Api
import com.example.myweatherapp.domain.location.LocationRepository
import com.example.myweatherapp.domain.locationModel.City
import com.example.myweatherapp.domain.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val api: Api,
    private val app: Application
) : LocationRepository {

    override suspend fun getCurrentLocation(): Location? {
        val locationManager = app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasFineLocationPermission =
            ContextCompat.checkSelfPermission(app, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLoccationPermission =
            ContextCompat.checkSelfPermission(app, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        val isGPSEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!hasFineLocationPermission || !hasCoarseLoccationPermission || !isGPSEnabled) return null

        return suspendCancellableCoroutine { con ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) con.resume(result)
                    else con.resume(null)
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener { con.resume(it) }
                addOnFailureListener { con.resume(null) }
                addOnCanceledListener { con.cancel() }
            }
        }
    }

    override suspend fun getCityNameFromGeocoder(
        latitude: Double,
        longitude: Double
    ): City? = suspendCancellableCoroutine { con ->
        val geocoder = Geocoder(app.applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, 1, object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    val city = addresses.firstOrNull()?.let {
                        City(
                            name = it.locality,
                            latitude = latitude,
                            longitude = longitude,
                            country = it.countryName
                        )
                    }
                    con.resume(city)
                }

                override fun onError(errorMessage: String?) {
                    con.resume(null)
                }
            })
        } else {
            try {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                val city = addresses?.firstOrNull()?.let {
                    City(
                        name = it.locality,
                        latitude = latitude,
                        longitude = longitude,
                        country = it.countryName
                    )
                }
                con.resume(city)
            } catch (e: Exception) {
                e.printStackTrace()
                con.resume(null)
            }
        }


    }

    suspend fun searchLocation(name: String): Resource<List<City>> {
        return try {
            Resource.Success(
                api.searchLocation(name = name, count = 10).toCityList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unexpected error happened.")
        }
    }

    override suspend fun searchCity(text: String): List<City> {
        return try {
            when (val result = searchLocation(name = text)) {
                is Resource.Success -> result.data ?: emptyList()
                is Resource.Error -> emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}