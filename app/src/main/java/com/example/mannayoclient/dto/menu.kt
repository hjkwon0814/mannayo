package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class menu(
    @SerializedName("id")
    @Expose
    val id : Long,

    @SerializedName("name")
    @Expose
    val name : String,

    @SerializedName("price")
    @Expose
    val price : Int,

    @SerializedName("isbest")
    @Expose
    val isbest : Boolean,

    @SerializedName("image")
    @Expose
    val image : String
)
