package com.example.mannayoclient

import com.example.mannayoclient.categorylist.restaurantImage
import com.example.mannayoclient.categorylist.restaurantInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface mannayoService {
    @POST("/members/findMyAccountByNickname")
    fun getMyAccountByNickname(@Body reqdata: SendNicknameRequestData): Call<resdata>

    @POST("/members/findMyAccountByPhoneNumber")
    fun getMyAccountByPhoneNumber(@Body reqdata: SendPhoneNumberRequestData): Call<resdata>

    @POST("/signup")
    fun signUp(@Body reqdata : signUpRequest): Call<resSignUpData>

    @GET("/signin")
    fun signIn(@Query("email") email :String, @Query("password") password : String) : Call<ReceiveLoginOK>

    @GET("/restaurant/type")
    fun getRestaurantList(@Query("type") type : String) : Call<List<restaurantInfo>>

    @GET("/restaurant/profileimage")
    @Streaming
    fun getRestaurantImage(@Query("id") id : Long) : Call<ResponseBody>

}