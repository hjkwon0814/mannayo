package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VoteResponseDto(
    @SerializedName("contents")
    @Expose
    val contents : String,

    @SerializedName("count")
    @Expose
    val count : Long,

    @SerializedName("amIVote")
    @Expose
    val amIVote : Boolean
)
