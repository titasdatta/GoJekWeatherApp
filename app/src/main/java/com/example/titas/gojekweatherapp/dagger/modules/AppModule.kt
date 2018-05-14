package com.example.titas.gojekweatherapp.dagger.modules

import android.app.Application
import android.content.Context
import com.example.titas.gojekweatherapp.common.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Titas on 5/14/2018.
 */
@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun providesUtils(): Utils = Utils(app)
}