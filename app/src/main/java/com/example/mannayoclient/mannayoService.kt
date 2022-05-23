package com.example.mannayoclient

import com.example.mannayoclient.categorylist.restaurantImage
import com.example.mannayoclient.categorylist.restaurantInfo
import com.example.mannayoclient.mainmenulist.restaurantDetailInfo
import com.example.mannayoclient.storereviewlist.ReviewList
import okhttp3.MultipartBody
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

    @GET("/restaurant/detail/{restaurantId}")
    fun getRestaurantDatailInfo(@Path("restaurantId") id :Long?) : Call<restaurantDetailInfo>

    @POST("/members/inputnickname")
    fun setNickname(@Query("id") id: Long?, @Query("nickname") nickname:String) : Call<ReceiveOK>

    @Multipart
    @POST("/members/profileimage")
    fun setMyProfileImage(@Query("id") id : Long?, @Part Image : MultipartBody.Part) : Call<ReceiveOK>

    @GET("/members/profileimage/{id}")
    @Streaming
    fun getMyProfileImage(@Path("id") id : Long?) : Call<ResponseBody>

    @GET("/reviews")
    fun getReviewList(@Query("restaurantId") id: Long?) :Call<List<ReviewList>>

    @GET("/reviews/image/{id}")
    @Streaming
    fun getReviewImage(@Path("id") id : Long?) : Call<ResponseBody>

}