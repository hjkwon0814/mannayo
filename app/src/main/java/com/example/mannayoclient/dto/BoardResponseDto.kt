package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BoardResponseDto(
    @SerializedName("memberId")
    @Expose
    val memberId : Long,

    @SerializedName("boardId")
    @Expose
    val boardId : Long,

    @SerializedName("nickName")
    @Expose
    val nickname : String,

    @SerializedName("contents")
    @Expose
    val contents : String,

    @SerializedName("date")
    @Expose
    val date : String,

    @SerializedName("image")
    @Expose
    val image : String,

    @SerializedName("boardType")
    @Expose
    val boardType : String,

    @SerializedName("likeCount")
    @Expose
    val likeCount : Long,

    @SerializedName("commentCount")
    @Expose
    val chatCount : Long,

    @SerializedName("isVote")
    @Expose
    val isVote : Boolean,

    @SerializedName("isProfile")
    @Expose
    val isProfile : Boolean
)
