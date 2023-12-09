package com.app10x.weatherapp.data.network

import com.app10x.weatherapp.data.responses.CurrentWeatherResponse
import com.app10x.weatherapp.data.responses.ForecastWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
   @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("APPID") apiKey: String,
    ): Response<CurrentWeatherResponse>

    @GET("forecast")
    suspend fun getForecastWeather(
        @Query("q") city: String,
        @Query("APPID") apiKey: String,
    ): Response<ForecastWeatherResponse>
}