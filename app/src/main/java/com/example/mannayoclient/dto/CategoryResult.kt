package com.example.mannayoclient.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.Setter

@Setter
@Getter
class CategoryResult() : Parcelable{


    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<CategoryResult> {
        override fun createFromParcel(parcel: Parcel): CategoryResult {
            return CategoryResult(parcel)
        }

        override fun newArray(size: Int): Array<CategoryResult?> {
            return arrayOfNulls(size)
        }
    }
}