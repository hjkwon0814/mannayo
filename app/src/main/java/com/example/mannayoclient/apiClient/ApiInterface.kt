package com.example.mannayoclient.apiClient

import com.example.mannayoclient.dto.CategoryResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiInterface {

    //내 위치 기준으로 카테고리로 검색하기

    //카테고리로 검색
    @GET("v2/local/search/category.json")
    fun getSearchCategory(
        @Header("Authorization") token: String,
        @Query("category_group_code") category_group_code: String,
        @Query("x") x: Double,
        @Query("y") y: Double,
        @Query("radius") radius: Int,
        @Query("page") page:Int
    ): Call<CategoryResult>
}