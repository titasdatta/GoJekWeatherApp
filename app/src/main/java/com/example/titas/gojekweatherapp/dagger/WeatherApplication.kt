package com.example.titas.gojekweatherapp.dagger

import android.app.Activity
import android.app.Application
import com.example.titas.gojekweatherapp.common.Constants
import com.example.titas.gojekweatherapp.dagger.components.AppComponent
import com.example.titas.gojekweatherapp.dagger.components.DaggerAppComponent
import com.example.titas.gojekweatherapp.dagger.modules.AppModule
import com.example.titas.gojekweatherapp.dagger.modules.NetModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Titas on 5/14/2018.
 */
class WeatherApplication: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(Constants.BASE_URL))
                .build()
        component.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}