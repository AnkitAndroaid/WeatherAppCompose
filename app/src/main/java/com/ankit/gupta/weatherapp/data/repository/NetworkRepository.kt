package com.ankit.gupta.weatherapp.data.repository
import com.ankit.gupta.weatherapp.data.network.WeatherApi
import com.ankit.gupta.weatherapp.data.responses.CurrentWeatherResponse
import com.ankit.gupta.weatherapp.data.responses.ForecastWeatherResponse
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    val weatherApi: WeatherApi
) {
    suspend fun getCurrentWeather(city: String, apiKey: String): Response<CurrentWeatherResponse> {
        return weatherApi.getCurrentWeather(city, apiKey)
    }
    suspend fun getForecastWeather(city: String, apiKey: String):Response<ForecastWeatherResponse>{
        return weatherApi.getForecastWeather(city,apiKey)
    }
}