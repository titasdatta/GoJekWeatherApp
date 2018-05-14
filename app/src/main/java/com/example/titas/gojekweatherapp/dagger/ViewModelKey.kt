package com.example.titas.gojekweatherapp.dagger

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Titas on 5/14/2018.
 */
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)