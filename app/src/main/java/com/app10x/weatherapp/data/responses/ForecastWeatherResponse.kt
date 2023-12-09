package com.app10x.weatherapp.data.responses

import com.google.gson.annotations.SerializedName


data class ForecastWeatherResponse (

  @SerializedName("cod"     ) var cod     : String?         = null,
  @SerializedName("message" ) var message : Int?            = null,
  @SerializedName("cnt"     ) var cnt     : Int?            = null,
  @SerializedName("list"    ) var dateList    : ArrayList<DateList> = arrayListOf(),
  @SerializedName("city"    ) var city    : City?           = City()

)