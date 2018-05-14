package com.example.titas.gojekweatherapp

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.titas.gojekweatherapp.common.Utils
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * Created by Titas on 5/15/2018.
 */
@RunWith(JUnit4::class)
class UtilsTest{
    private lateinit var utils: Utils

    @Before
    fun setup(){
        val context = Mockito.mock<Context>(Context::class.java)
        utils = Utils(context)
    }

    @Test
    fun getFormattedDayForTest(){
        Assert.assertEquals("Monday", utils.getFormattedDayFor("2018-05-14"))
        Assert.assertEquals("Tuesday", utils.getFormattedDayFor("2018-05-15"))
        Assert.assertEquals("Wednesday", utils.getFormattedDayFor("2018-05-16"))
        Assert.assertEquals("Thursday", utils.getFormattedDayFor("2018-05-17"))
        Assert.assertEquals("Friday", utils.getFormattedDayFor("2018-05-18"))
        Assert.assertEquals("Saturday", utils.getFormattedDayFor("2018-05-19"))
        Assert.assertEquals("Sunday", utils.getFormattedDayFor("2018-05-20"))
    }

    @Test
    fun isNetworkAvailableTest(){
        val context = Mockito.mock<Context>(Context::class.java)
        val connManager = Mockito.mock(ConnectivityManager::class.java)
        val networkInfo = Mockito.mock(NetworkInfo::class.java)
        val packageManager = Mockito.mock(PackageManager::class.java)
        val utils = Utils(context)

        Mockito.`when`(context.packageManager).thenReturn(packageManager)
        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connManager)
        Mockito.`when`(connManager.activeNetworkInfo).thenReturn(networkInfo)
        Mockito.`when`(networkInfo.isAvailable).thenReturn(true)
        Mockito.`when`(networkInfo.isConnected).thenReturn(true)

        Assert.assertTrue(utils.isNetworkAvailable())

        Mockito.`when`(networkInfo.isConnected).thenReturn(false)

        Assert.assertFalse(utils.isNetworkAvailable())
    }
}