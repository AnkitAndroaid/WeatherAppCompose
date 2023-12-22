package com.ankit.gupta.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ankit.gupta.weatherapp.R
import com.ankit.gupta.weatherapp.ui.model.ForecastWeatherData

class ForecastAdapter(private val forecastList: List<ForecastWeatherData>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val forecastDay: TextView = view.findViewById(R.id.tv_forecast_day)
        val forecastTemp: TextView = view.findViewById(R.id.tv_forecast_temperature)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_forecast_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = forecastList[position]
        holder.forecastDay.text = item.day
        holder.forecastTemp.text = item.temp
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}