package com.app10x.weatherapp.data.repository
import com.app10x.weatherapp.data.network.WeatherApi
import com.app10x.weatherapp.data.responses.CurrentWeatherResponse
import com.app10x.weatherapp.data.responses.ForecastWeatherResponse
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