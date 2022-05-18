package com.example.mannayoclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitService {
    private const val BASE_URL = "http://192.168.0.2:8080"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service:mannayoService = retrofit.create(mannayoService::class.java)
}