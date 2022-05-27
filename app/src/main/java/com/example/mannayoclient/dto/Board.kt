package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import lombok.Setter

data class Board (

    @SerializedName("memberId")
    @Expose
    val memberId: Long?,

    @SerializedName("title")
    @Expose
    val title:String,

    @SerializedName("contents")
    @Expose
    val contents:String,

    @SerializedName("isVote")
    @Expose
    val isVote:Boolean,

    @SerializedName("type")
    @Expose
    val type:String
)


