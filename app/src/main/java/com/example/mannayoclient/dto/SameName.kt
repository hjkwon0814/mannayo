package com.example.mannayoclient.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter @NoArgsConstructor
@Setter @AllArgsConstructor
data class SameName(

    @SerializedName("region")
    @Expose
    private var region: List<Any>,

    @SerializedName("keyword")
    @Expose
    private var keyword: String,

    @SerializedName("selected_region")
    @Expose
    private var selectedRegion: String

)