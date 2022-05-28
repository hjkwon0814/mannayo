package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReviewList(
    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("memberId")
    @Expose
    val memberId: Long,

    @SerializedName("content")
    @Expose
    val content: String,

    @SerializedName("writeDate")
    @Expose
    val writeDate: String,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("starPoint")
    @Expose
    val starPoint: Float,

    @SerializedName("isDeleted")
    @Expose
    val isDeleted: Boolean,

    @SerializedName("isModified")
    @Expose
    val isModified: Boolean,

    @SerializedName("memberImage")
    @Expose
    val memberImage: String,

    @SerializedName("memberNickname")
    @Expose
    val memberNickname: String
)
