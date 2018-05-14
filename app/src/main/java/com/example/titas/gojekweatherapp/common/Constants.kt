package com.example.titas.gojekweatherapp.common

/**
 * Created by Titas on 5/14/2018.
 */
class Constants {
    companion object {
        const val API_KEY: String = "f8ea755e72754a7882f92757180805"
        const val BASE_URL: String = "http://api.apixu.com/v1/"
        const val PERMISSION_REQUEST_CODE: Int = 1001
        const val LOCATION_REFRESH_TIME: Long = 5 * 60 * 1000
        const val LOCATION_REFRESH_DISTANCE: Float = 5000f
    }
}