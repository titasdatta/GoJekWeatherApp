package com.example.titas.gojekweatherapp.dagger.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.titas.gojekweatherapp.dagger.ViewModelKey
import com.example.titas.gojekweatherapp.viewmodel.ForecastListViewModel
import com.example.titas.gojekweatherapp.viewmodel.ForecastViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Titas on 5/14/2018.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ForecastListViewModel::class)
    internal abstract fun bindForecastListViewModel(forecastListViewModel: ForecastListViewModel): ViewModel

    @Binds
    internal abstract fun bindForecastViewModelFactory(factory: ForecastViewModelFactory) : ViewModelProvider.Factory
}