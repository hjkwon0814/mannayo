package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class signUpRequest(
    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("password")
    @Expose
    val password: String,

    @SerializedName("realname")
    @Expose
    val realname: String,

    @SerializedName("birth")
    @Expose
    val birth : String,

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String,

    @SerializedName("loginTypeEnum")
    @Expose
    val loginTypeEnum : String,

    @SerializedName("imageAddress")
    @Expose
    val image : String,

    @SerializedName("accountTypeEnum")
    @Expose
    val accountTypeEnum : String,

    @SerializedName("accountStatus")
    @Expose
    val accountStatus : String
)
