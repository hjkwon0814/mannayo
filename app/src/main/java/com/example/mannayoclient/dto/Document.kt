package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Document (
    @SerializedName("place_name")
    @Expose
    private val placeName: String,

    @SerializedName("distance")
    @Expose
    private val distance: String,

    @SerializedName("place_url")
    @Expose
    private val placeUrl: String,

    @SerializedName("category_name")
    @Expose
    private val categoryName: String,

    @SerializedName("address_name")
    @Expose
    private val addressName: String,

    @SerializedName("road_address_name")
    @Expose
    private val roadAddressName: String,

    @SerializedName("id")
    @Expose
    private val id: String,

    @SerializedName("phone")
    @Expose
    private val phone: String,

    @SerializedName("category_group_code")
    @Expose
    private val categoryGroupCode: String,

    @SerializedName("category_group_name")
    @Expose
    private val categoryGroupName: String,

    @SerializedName("x")
    @Expose
    private val x: String,

    @SerializedName("y")
    @Expose
    private val y: String
){
    fun getPlaceName():String{
        return placeName
    }
    fun getX():Double{
        return x.toDouble()
    }
    fun getY():Double{
        return y.toDouble()
    }
}