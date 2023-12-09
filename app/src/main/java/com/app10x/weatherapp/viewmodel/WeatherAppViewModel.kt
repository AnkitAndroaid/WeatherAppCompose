package com.app10x.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app10x.weatherapp.BuildConfig
import com.app10x.weatherapp.data.repository.NetworkRepository
import com.app10x.weatherapp.data.responses.CurrentWeatherResponse
import com.app10x.weatherapp.data.responses.ForecastWeatherResponse
import com.app10x.weatherapp.ui.model.CurrentWeatherData
import com.app10x.weatherapp.ui.model.ForecastWeatherData
import com.app10x.weatherapp.util.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.jvm.internal.Intrinsics.Kotlin


@HiltViewModel
class WeatherAppViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _currentWeather = MutableLiveData<DataHandler<CurrentWeatherResponse>>()
    val currentWeather: LiveData<DataHandler<CurrentWeatherResponse>> = _currentWeather
    private val _forecastWeather = MutableLiveData<DataHandler<ForecastWeatherResponse>>()
    val forecastWeather: LiveData<DataHandler<ForecastWeatherResponse>> = _forecastWeather

    //    Configurable city. can use location awareness to get this value
    val city = "Bengaluru"

    //    //    method to invoke current weather response and observe using livedata
    fun getCurrentWeather() {
        _currentWeather.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getCurrentWeather(city, BuildConfig.OPEN_WEATHER_KEY)
            _currentWeather.postValue(handleCurrentWeatherResponse(response))
        }
    }

    //    method to invoke forecast weather response and observe using livedata
    fun getForecastWeather() {
        _forecastWeather.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getForecastWeather(city, BuildConfig.OPEN_WEATHER_KEY)
            _forecastWeather.postValue(handleForecastWeatherResponse(response))
        }
    }

    //    For handling current weather response
    private fun handleCurrentWeatherResponse(response: Response<CurrentWeatherResponse>): DataHandler<CurrentWeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->

                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    //    For handling Forecast Weather response
    private fun handleForecastWeatherResponse(response: Response<ForecastWeatherResponse>): DataHandler<ForecastWeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    fun getCurrentWeatherData(data: CurrentWeatherResponse?): CurrentWeatherData {
        data?.main?.temp?.let {
            return CurrentWeatherData(data.name!!, kelvinToCelsius(it).toString())
        }
        return return CurrentWeatherData("Error", "Error")
    }


    fun kelvinToCelsius(value: Double): Double {
        return (value - 273.15f).roundTo(2)
    }

    fun Number.roundTo(
        numFractionDigits: Int
    ) = "%.${numFractionDigits}f".format(this, Locale.ENGLISH).toDouble()

    fun getForecastWeatherData(data: ForecastWeatherResponse?): List<ForecastWeatherData?>? {

        // Get the current date
        val newDataList = data?.dateList?.filterIndexed { index, _ ->  index == 0 || (index + 1) % 8 == 0 }?.takeLast(4)



        val forecastWeatherData = newDataList?.map { list ->
            list.main?.temp?.let {
                ForecastWeatherData(getDay(list.dt), kelvinToCelsius(it).toString())
            }
        }

        return forecastWeatherData
    }

    private fun getDay(dt: Int?): String {
       var day = "Error"
        dt?.let {  day = SimpleDateFormat("EEEE", Locale.ENGLISH).format(it * 1000)}
        return day
    }


}