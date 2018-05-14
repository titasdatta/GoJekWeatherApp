package com.example.titas.gojekweatherapp.dagger.modules

import com.example.titas.gojekweatherapp.model.repository.network.WeatherService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Titas on 5/14/2018.
 */
@Module
class NetModule(private val baseUrl: String) {

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherService
            = retrofit.create<WeatherService>(WeatherService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl).build()

    }



}