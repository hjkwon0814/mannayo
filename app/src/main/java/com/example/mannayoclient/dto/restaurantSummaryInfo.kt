package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class restaurantSummaryInfo(

    @SerializedName("name")
    @Expose
    val name:String,

    @SerializedName("address")
    @Expose
    val address : String,

    @SerializedName("open")
    @Expose
    val open : String,

    @SerializedName("close")
    @Expose
    val close :String,

    @SerializedName("isExist")
    @Expose
    val isExist : Boolean

)
