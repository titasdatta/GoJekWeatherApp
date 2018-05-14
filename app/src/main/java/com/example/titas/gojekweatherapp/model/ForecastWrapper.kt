package com.example.titas.gojekweatherapp.model

/**
 * Created by Titas on 5/14/2018.
 */
class ForecastWrapper(private val weatherResponse: WeatherResponse?){
    lateinit var city: String
    var currentTemp:Float = 0f
    lateinit var forecastList: ArrayList<WeatherForecast>
    var status: StatusCode
    init {
        if(weatherResponse != null){
            city = weatherResponse.location.cityName
            currentTemp = weatherResponse.current.temp
            forecastList = weatherResponse.forecast.forecastList
            status = StatusCode.OK
        }else{
            status = StatusCode.FAIL
        }
    }
}

enum class StatusCode{
    OK, FAIL
}