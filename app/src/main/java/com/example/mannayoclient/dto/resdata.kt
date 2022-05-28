package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class resdata(
    @SerializedName("email")
    @Expose
    val email : String
)
