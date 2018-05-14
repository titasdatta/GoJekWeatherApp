package com.example.titas.gojekweatherapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.titas.gojekweatherapp.common.Constants
import com.example.titas.gojekweatherapp.model.*
import com.example.titas.gojekweatherapp.model.repository.network.WeatherService
import junit.framework.Assert
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

/**
 * Created by Titas on 5/15/2018.
 */
@RunWith(JUnit4::class)
class WeatherServiceTest {
    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()

    private lateinit var weatherService: WeatherService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()

        weatherService = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(WeatherService::class.java)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun getWeatherResponseTest(){
        val content = this.javaClass.classLoader
                .getResource("get_weather_response_cases/valid.json")
                .readText(Charset.forName("UTF-8"))

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(content))

        val location = Location("Bengaluru")
        val current = Current(22.0f)
        val forecastList = arrayListOf<WeatherForecast>()
        forecastList.add(WeatherForecast("2018-05-13",
                Day(32.1f, 25.4f)))
        forecastList.add(WeatherForecast("2018-05-14",
                Day(32.2f, 24.7f)))
        forecastList.add(WeatherForecast("2018-05-15",
                Day(33.0f, 25.0f)))
        forecastList.add(WeatherForecast("2018-05-16",
                Day(34.5f, 25.1f)))
        forecastList.add(WeatherForecast("2018-05-17",
                Day(32.4f, 24.5f)))
        forecastList.add(WeatherForecast("2018-05-18",
                Day(35.1f, 25.3f)))
        forecastList.add(WeatherForecast("2018-05-19",
                Day(32.4f, 24.4f)))
        val forecast = Forecast(forecastList)
        val mockWeatherResponse = WeatherResponse(location, current, forecast)

        val weatherResponse = weatherService.getWeather(Constants.API_KEY, "bengaluru", 7).execute()
        Assert.assertTrue(weatherResponse.isSuccessful)
        Assert.assertNotNull(weatherResponse.body())
        Assert.assertEquals(weatherResponse.body(), mockWeatherResponse)
    }
}