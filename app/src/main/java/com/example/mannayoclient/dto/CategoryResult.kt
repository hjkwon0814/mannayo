package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CategoryResult(
    @SerializedName("meta")
    @Expose
    private val meta: Meta,

    @SerializedName("documents")
    @Expose
    private val documents: List<Document>,

){
    fun getDocuments(): List<Document> {
        return documents
    }

    fun getMeta(): Meta{
        return meta
    }
}