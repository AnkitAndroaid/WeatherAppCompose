package com.ankit.gupta.weatherapp.data.responses

import com.google.gson.annotations.SerializedName


data class Rain (

  @SerializedName("3h" ) var rain : Double? = null

)