package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class review(
    @SerializedName("memberId")
    @Expose
    val memberId : Long?,

    @SerializedName("restaurantId")
    @Expose
    val restaurantId : Long?,

    @SerializedName("content")
    @Expose
    val content : String,

    @SerializedName("starPoint")
    @Expose
    val starPoint : Float
)
