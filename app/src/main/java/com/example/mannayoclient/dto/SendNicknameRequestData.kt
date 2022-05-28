package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendNicknameRequestData(
    @SerializedName("nickName")
    @Expose
    val nickname: String,
    @SerializedName("realName")
    @Expose
    val realname: String
)
