package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class restaurantDetailInfo(
    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("number")
    @Expose
    val number: String,

    @SerializedName("address")
    @Expose
    val address: String,

    @SerializedName("start_time")
    @Expose
    val start_time: String,

    @SerializedName("end_time")
    @Expose
    val end_time: String,

    @SerializedName("jjimcount")
    @Expose
    val jjimcount: Int,

    @SerializedName("owner")
    @Expose
    val owner: String,

    @SerializedName("starPoint")
    @Expose
    val starPoint: Float
)
