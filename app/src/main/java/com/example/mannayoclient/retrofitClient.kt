package com.example.mannayoclient

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitService {
    private const val BASE_URL = "http://ec2-54-180-120-109.ap-northeast-2.compute.amazonaws.com:8080"

    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val service:mannayoService = retrofit.create(mannayoService::class.java)
}