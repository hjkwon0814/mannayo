package com.example.mannayoclient

import com.example.mannayoclient.categorylist.restaurantImage
import com.example.mannayoclient.categorylist.restaurantInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    @GET("/restaurant/image")
    fun getRestaurantImage(@Query("id") id : Long) : Call<restaurantImage>

}