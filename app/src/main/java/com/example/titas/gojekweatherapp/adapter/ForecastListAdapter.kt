package com.example.titas.gojekweatherapp.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.view.ViewGroup
import com.example.titas.gojekweatherapp.R
import com.example.titas.gojekweatherapp.common.Utils
import com.example.titas.gojekweatherapp.common.inflate
import com.example.titas.gojekweatherapp.model.WeatherForecast
import kotlinx.android.synthetic.main.forecast_row_item.view.*
import javax.inject.Inject

/**
 * Created by Titas on 5/14/2018.
 */
class ForecastListAdapter @Inject constructor(private val forecastList: ArrayList<WeatherForecast>,
                                              val utils: Utils): RecyclerView.Adapter<ForecastListAdapter.ForecastListViewHolder>() {

    init {
        if(forecastList != null && forecastList.size > 1){
            forecastList.removeAt(0)
        }
    }

    override fun onBindViewHolder(holder: ForecastListViewHolder?, position: Int) {
        val forecast = forecastList[position]
        holder?.let {
            holder.bind(forecast, utils)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder {
        val rowView = parent.inflate(R.layout.forecast_row_item, false)
        return ForecastListViewHolder(rowView)
    }

    override fun getItemCount(): Int = forecastList.size

    class ForecastListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var mView: View = view
        private var forecast: WeatherForecast? = null

        fun bind(forecast: WeatherForecast, utils: Utils){
            this.forecast = forecast
            val avg_temp = (forecast.day.minTemp+forecast.day.maxTemp)/2
            val avg_temp_text = "${avg_temp}"+"°C"
            mView.temp.text = Html.fromHtml(avg_temp_text)
            mView.day.text = utils.getFormattedDayFor(forecast.forecastDate)
        }
    }
}