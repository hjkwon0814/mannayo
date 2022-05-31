package com.example.mannayoclient.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.Setter

@Getter
@Setter
class SameName()  {

    @SerializedName("region")
    @Expose
    private var region: List<Any>? = null

    @SerializedName("keyword")
    @Expose
    private var keyword: String? = null

    @SerializedName("selected_region")
    @Expose
    private var selectedRegion: String? = null


}