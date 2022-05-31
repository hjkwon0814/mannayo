package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NoticeDto(
    @SerializedName("title")
    @Expose
    val title : String,

    @SerializedName("contents")
    @Expose
    val contents : String,

    @SerializedName("date")
    @Expose
    val date : String
)
