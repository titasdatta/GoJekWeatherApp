package com.example.titas.gojekweatherapp.view

import android.Manifest
import android.animation.Animator
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.TranslateAnimation
import com.example.titas.gojekweatherapp.R
import com.example.titas.gojekweatherapp.adapter.ForecastListAdapter
import com.example.titas.gojekweatherapp.common.Constants.Companion.LOCATION_REFRESH_DISTANCE
import com.example.titas.gojekweatherapp.common.Constants.Companion.LOCATION_REFRESH_TIME
import com.example.titas.gojekweatherapp.common.Constants.Companion.PERMISSION_REQUEST_CODE
import com.example.titas.gojekweatherapp.common.Utils
import com.example.titas.gojekweatherapp.dagger.WeatherApplication
import com.example.titas.gojekweatherapp.model.ForecastWrapper
import com.example.titas.gojekweatherapp.model.StatusCode
import com.example.titas.gojekweatherapp.model.WeatherForecast
import com.example.titas.gojekweatherapp.viewmodel.ForecastListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    private lateinit var weatherResponseLiveData : LiveData<ForecastWrapper>
    private var forecastListAdapter: ForecastListAdapter? = null
    private lateinit var forecastViewModel: ForecastListViewModel
    private var forecastList: ArrayList<WeatherForecast> = arrayListOf()
    @Inject
    lateinit var utils: Utils
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val locationListener: LocationListener = object : LocationListener {
        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onLocationChanged(location: Location?) {
            if(location != null){
                fetchWeatherForLocation(location)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastViewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastListViewModel::class.java)
        weatherResponseLiveData = forecastViewModel.getWeatherObservable()
        weatherResponseLiveData.observe(this, object : Observer<ForecastWrapper> {
            override fun onChanged(t: ForecastWrapper?) {
                hideProgress()
                if(t?.status == StatusCode.OK){
                    updateForecastScreen(t)
                }else{
                    showFailureScreen()
                }
            }
        })

        val layoutManager = LinearLayoutManager(this)
        forecast_list.layoutManager = layoutManager
        forecast_list.setHasFixedSize(true)
    }

    private fun fetchWeatherForLocation(location: Location){
        val queryString = "${location.latitude},${location.longitude}"
        forecastViewModel.getWeather(queryString)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission granted by user
                    setupLocationManager()
                }else{
                    //permission denied by user
                    //TODO: Or maybe show error screen to block the user from using the app
                    //TODO: without giving location permission
                    forecastViewModel.getWeather("bengaluru")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupLocationManager()
    }

    override fun onPause() {
        super.onPause()
        utils.locationManager.removeUpdates(locationListener)
    }

    /**
     * Hides the progress loader
     */
    private fun hideProgress(){
        //animate this later
        progress.animate().alpha(0.0f).setDuration(200).setListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                progress.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
    }

    private fun showProgress(){
        progress.visibility = View.VISIBLE
        progress.alpha = 1.0f
    }

    private fun updateForecastScreen(forecastData: ForecastWrapper?){
        forecastData?.let {
            val currentTemp = "${forecastData.currentTemp.toInt()}" + "\u00B0"
            current_temp.text = "${currentTemp}"
            current_location.text = "${forecastData.city}"
            forecastList.clear()
            forecastData.forecastList.removeAt(0)
            forecastList.addAll(forecastData.forecastList)
            success_screen.visibility = View.VISIBLE
            if(forecastListAdapter == null) {
                forecastListAdapter = ForecastListAdapter(forecastList, utils)
                forecast_list.adapter = forecastListAdapter
                forecast_list.visibility = View.INVISIBLE
                //animate list from below
                slideUp()
            }else{
                forecastListAdapter?.notifyDataSetChanged()
            }
        }
    }

    private fun slideUp(){
        val handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                forecast_list.visibility = View.VISIBLE
                val translateAnimation = TranslateAnimation(0f, 0f, forecast_list.height.toFloat(), 0f)
                translateAnimation.duration = 600
                translateAnimation.interpolator = AccelerateDecelerateInterpolator()
                translateAnimation.fillAfter = true
                forecast_list.startAnimation(translateAnimation)
            }
        }, 500)
    }

    private fun showFailureScreen(){
        error_screen.visibility = View.VISIBLE
        success_screen.visibility = View.GONE
        retry_btn.setOnClickListener {
            error_screen.visibility = View.GONE
            showProgress()
            setupLocationManager()
        }
    }

    private fun setupLocationManager(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            utils.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener)
            try {
                val location = utils.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(location != null){
                    fetchWeatherForLocation(location)
                }
            }catch (e: Exception){
                Log.d("WeatherLogs", "Error fetching last location:${e.message}")
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }
}
