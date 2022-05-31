package com.example.mannayoclient.apiClient

import com.example.mannayoclient.retrofitService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    private const val BASE_URL = "https://dapi.kakao.com/"
    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val service:ApiInterface = retrofit.create(ApiInterface::class.java)
}