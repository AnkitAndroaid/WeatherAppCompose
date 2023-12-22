package com.ankit.gupta.weatherapp.data.responses

import com.google.gson.annotations.SerializedName


data class Clouds (

  @SerializedName("all" ) var all : Int? = null

)