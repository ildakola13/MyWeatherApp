package com.example.myweatherapp.di

import com.example.myweatherapp.data.location.LocationRepositoryImpl
import com.example.myweatherapp.domain.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: LocationRepositoryImpl): LocationRepository
}