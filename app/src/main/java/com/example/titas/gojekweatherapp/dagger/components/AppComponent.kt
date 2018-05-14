package com.example.titas.gojekweatherapp.dagger.components

import com.example.titas.gojekweatherapp.dagger.WeatherApplication
import com.example.titas.gojekweatherapp.dagger.modules.AppModule
import com.example.titas.gojekweatherapp.dagger.modules.NetModule
import com.example.titas.gojekweatherapp.dagger.modules.ViewModelModule
import com.example.titas.gojekweatherapp.view.WeatherActivity
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Titas on 5/14/2018.
 */
@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, ViewModelModule::class, AppModule::class, NetModule::class))
interface  AppComponent{
    fun inject(application: WeatherApplication)
    fun inject(activity: WeatherActivity)
}