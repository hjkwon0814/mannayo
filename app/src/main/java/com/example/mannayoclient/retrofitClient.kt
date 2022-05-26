package com.example.mannayoclient

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitService {

//    private const val BASE_URL = "http://192.168.219.101:8080" //gongseunghwa ip
      private const val BASE_URL = "http://58.225.185.45:8080"



    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val service:mannayoService = retrofit.create(mannayoService::class.java)
}