package com.example.titas.gojekweatherapp.model.repository.network

import com.example.titas.gojekweatherapp.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Titas on 5/14/2018.
 */
interface WeatherService {
    @GET("forecast.json")
    fun getWeather(@Query("key") key: String, @Query("q") queryString: String, @Query("days") dayCount: Int) : Call<WeatherResponse>
}