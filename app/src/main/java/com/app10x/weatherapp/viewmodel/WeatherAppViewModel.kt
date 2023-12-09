package com.app10x.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app10x.weatherapp.BuildConfig
import com.app10x.weatherapp.data.repository.NetworkRepository
import com.app10x.weatherapp.data.responses.CurrentWeatherResponse
import com.app10x.weatherapp.data.responses.ForecastWeatherResponse
import com.app10x.weatherapp.util.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


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
    fun getForecastWeather(){
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
}