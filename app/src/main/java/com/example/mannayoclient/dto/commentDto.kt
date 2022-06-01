package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class commentDto(
    @SerializedName("id")
    @Expose
    val id : Long,

    @SerializedName("nickname")
    @Expose
    val nickname : String,

    @SerializedName("date")
    @Expose
    val date : String,

    @SerializedName("contents")
    @Expose
    val contents : String,

    @SerializedName("depth")
    @Expose
    val depth : Int
)
