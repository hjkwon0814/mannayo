package com.example.mannayoclient.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.Setter

@Getter
@Setter
data class Meta(

    @SerializedName("same_name")
    @Expose
    private val sameName: SameName,

    @SerializedName("pageable_count")
    @Expose
    private val pageableCount: Int,

    @SerializedName("total_count")
    @Expose
    private val totalCount: Int,

    @SerializedName("is_end")
    @Expose
    private val isEnd: Boolean,

    ){
    fun getIsEnd(): Boolean {
        return isEnd
    }
}