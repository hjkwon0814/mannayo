package com.example.mannayoclient

import com.example.mannayoclient.dto.*
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
    fun getRestaurantList(@Query("type") type : String?, @Query("memberId") memberId : Long?) : Call<List<restaurantInfo>>

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

    @Multipart
    @POST("/reviews/reviewimage")
    fun setReviewImage(@Query("id") id : Long?, @Part Image : MultipartBody.Part) : Call<ReceiveOK>

    //write board
    @Multipart
    @POST("/board/boardimages")
    fun setBoardImage(@Query("id") id:Long?, @Part Image : MultipartBody.Part) : Call<ReceiveOK>

    @POST("/board")
    fun setBoard(@Body board:Board) :Call<ReceiveOK>

    @GET("/members/profileimage/{id}")
    @Streaming
    fun getMyProfileImage(@Path("id") id : Long?) : Call<ResponseBody>

    @GET("/reviews/{restaurantId}")
    fun getReviewList(@Path("restaurantId") id: Long?) :Call<List<ReviewList>>

    @GET("/reviews/image/{id}")
    @Streaming
    fun getReviewImage(@Path("id") id : Long?) : Call<ResponseBody>

    @POST("/reviews")
    fun setReview(@Body review: review) : Call<ReceiveOK>

    @GET("/menu/{id}")
    fun getMenu(@Path("id") id: Long?) : Call<List<menu>>

    @GET("/menu/image/{id}")
    @Streaming
    fun getMenuImage(@Path("id") id: Long?) : Call<ResponseBody>

    @POST("/jjim")
    fun setJjim(@Query("memberid") memberid :Long?, @Query("restaurantid") restaurantid : Long?) : Call<ReceiveOK>

    @DELETE("/jjim/delete")
    fun deleteJjim(@Query("memberid") memberid :Long?, @Query("restaurantid") restaurantid : Long?) : Call<ReceiveOK>

    @GET("/restaurant/restaurantJjim")
    fun getRestaurantJjimList(@Query("memberId") memberId : Long?) : Call<List<restaurantInfo>>

    @GET("/board")
    fun getBoardList(@Query("boardType") boardType : String?) : Call<List<BoardResponseDto>>

    @GET("/board/image/{boardId}")
    @Streaming
    fun getBoardImage(@Path("boardId") boardId: Long?) : Call<ResponseBody>

    @GET("/board/{id}")
    fun getBoard(@Path("id") boardId : Long?) : Call<BoardResponseDto>

    @GET("/vote/{boardid}")
    fun getVote(@Path("boardid") boardId: Long?, @Query("memberId") memberId: Long?) : Call<List<VoteResponseDto>>

    @GET("/comment")
    fun getComment(@Query("boardId") boardId: Long?) : Call<List<commentDto>>

    @POST("/vote/{boardid}")
    fun setVote(@Path("boardid") boardId: Long?, @Query("contents") contents: String) : Call<ReceiveOK>
}