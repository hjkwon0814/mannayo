package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendPhoneNumberRequestData(
    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String,
    @SerializedName("realName")
    @Expose
    val realname : String
)
