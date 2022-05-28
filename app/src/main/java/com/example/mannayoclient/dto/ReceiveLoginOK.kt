package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReceiveLoginOK(
    @SerializedName("success")
    @Expose
    val success: Boolean,

    @SerializedName("code")
    @Expose
    val code: Int,

    @SerializedName("msg")
    @Expose
    val response: String,

    @SerializedName("data")
    @Expose
    val data: String,

    @SerializedName("nickname")
    @Expose
    val nickname: String,

    @SerializedName("id")
    @Expose
    val id: Long
)
