package com.example.titas.gojekweatherapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.titas.gojekweatherapp.common.Constants
import com.example.titas.gojekweatherapp.model.ForecastWrapper
import com.example.titas.gojekweatherapp.model.repository.WeatherRepository
import javax.inject.Inject

/**
 * Created by Titas on 5/14/2018.
 */
class ForecastListViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    private var forecastData: LiveData<ForecastWrapper>

    init {
        forecastData = repository.getWeatherObservable()
    }

    fun getWeather(queryString: String) = repository.getWeather(Constants.API_KEY, queryString, 5)

    fun getWeatherObservable(): LiveData<ForecastWrapper> {
        return forecastData
    }
}