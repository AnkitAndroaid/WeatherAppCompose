package com.app10x.weatherapp.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app10x.weatherapp.R
import com.app10x.weatherapp.ui.adapter.ForecastAdapter
import com.app10x.weatherapp.ui.model.ForecastWeatherData
import com.app10x.weatherapp.util.DataHandler
import com.app10x.weatherapp.viewmodel.WeatherAppViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherAppActivity : AppCompatActivity() {


    private lateinit var adapter: ForecastAdapter
    private lateinit var viewModel: WeatherAppViewModel
    private lateinit var parentLayout:ConstraintLayout
    private lateinit var snackbar: Snackbar

    private lateinit var ivLoading : ImageView
    private lateinit var tvTemp:TextView
    private lateinit var tvCity:TextView
    private lateinit var rvForecastList:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_app)

        parentLayout = findViewById(R.id.parent)
        ivLoading = findViewById(R.id.iv_loading)
        tvTemp = findViewById(R.id.tv_temperature)
        tvCity = findViewById(R.id.tv_city)
        rvForecastList = findViewById(R.id.rv_forecast_list)

        viewModel = ViewModelProvider(this).get(WeatherAppViewModel::class.java)
        setupSnackBar()

        viewModel.currentWeather.observe(this) { dataHandler ->
            when (dataHandler) {
                is DataHandler.ERROR -> {
                    Log.i("MyTag","Error")
                    snackbar.show()
                }
                is DataHandler.SUCCESS -> {
                    val currentData =  viewModel.getCurrentWeatherData(dataHandler.data)
                    tvCity.text = currentData.city
                    tvTemp.text = currentData.temperature
                    ivLoading.visibility = View.GONE
                    tvCity.visibility = View.VISIBLE
                    tvTemp.visibility = View.VISIBLE

                }
                is DataHandler.LOADING->{
                    ivLoading.visibility = View.VISIBLE
                    tvTemp.visibility = View.INVISIBLE
                    tvCity.visibility = View.INVISIBLE
                }

                else -> {

                }
            }
        }

        viewModel.forecastWeather.observe(this) { dataHandler ->
            when (dataHandler) {
                is DataHandler.ERROR -> {
                    snackbar.show()
                }
                is DataHandler.SUCCESS -> {
                    val forecastWeatherData: List<ForecastWeatherData> = viewModel.getForecastWeatherData(dataHandler.data) as List<ForecastWeatherData>
                    loadDataIntoRecyclerView(forecastWeatherData)
                    ivLoading.visibility = View.GONE
                    rvForecastList.visibility = View.VISIBLE

                }
                is DataHandler.LOADING->{
                    ivLoading.visibility = View.VISIBLE
                    rvForecastList.visibility = View.INVISIBLE
                }
                else -> {}
            }
        }
    callApi()
    }

    private fun loadDataIntoRecyclerView(forecastWeatherData: List<ForecastWeatherData>) {
        rvForecastList.layoutManager = LinearLayoutManager(this)
        adapter = ForecastAdapter(forecastWeatherData)
        rvForecastList.adapter = adapter
    }

    private fun setupSnackBar() {
        snackbar = Snackbar.make(
            parentLayout,
            resources.getString(R.string.something_went_wrong),
            Snackbar.LENGTH_LONG
        )
        val view = snackbar.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.BOTTOM
        view.layoutParams = params
        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        snackbar.setBackgroundTint(resources.getColor(R.color.color_2a2a2a, theme))
        snackbar.setTextColor(resources.getColor(R.color.white, theme))
        snackbar.setActionTextColor(resources.getColor(R.color.color_red, theme))
        snackbar.setAction(resources.getString(R.string.retry)) {
            callApi()
        }

    }

    private fun callApi() {
        viewModel.getCurrentWeather()
        viewModel.getForecastWeather()
    }
}
