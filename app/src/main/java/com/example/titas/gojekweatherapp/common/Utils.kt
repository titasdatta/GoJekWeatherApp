package com.example.titas.gojekweatherapp.common

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Titas on 5/14/2018.
 */
@Singleton
class Utils @Inject constructor(private val context: Context) {
    enum class Days(val day: String){
        SUNDAY("Sunday"), MONDAY("Monday"), TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"), THURSDAY("Thursday"), FRIDAY("Friday"),
        SATURDAY("Saturday")
    }

    val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun getFormattedDayFor(dateString: String): String{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = simpleDateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayOrdinal =  calendar.get(Calendar.DAY_OF_WEEK)
        return Days.values()[dayOrdinal].day
    }

    fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isAvailable && activeNetwork.isConnected
    }
}