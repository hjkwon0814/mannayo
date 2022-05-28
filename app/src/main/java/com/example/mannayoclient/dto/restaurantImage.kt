package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class restaurantImage(
    @SerializedName("image")
    @Expose
    val image: String
)
