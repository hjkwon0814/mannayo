package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class restaurantInfo(
    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName("starttime")
    @Expose
    val starttime: String,

    @SerializedName("endtime")
    @Expose
    val endtime: String,

    @SerializedName("point")
    @Expose
    val point: Float,

    @SerializedName("address")
    @Expose
    val address: String,

    @SerializedName("imageAddress")
    @Expose
    val imageAddress: String,

    @SerializedName("isJjim")
    @Expose
    val isJjim: Boolean,

    @SerializedName("countReview")
    @Expose
    val countReview: Long,

    @SerializedName("countJjim")
    @Expose
    val countJjim: Long
)
