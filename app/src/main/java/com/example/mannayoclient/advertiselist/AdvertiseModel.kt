package com.example.mannayoclient.advertiselist

import android.graphics.Bitmap

class AdvertiseModel (
    var nickname: String,
    var mainText: String,
    var date: String,
    var image: Bitmap,
    var like : Long,
    var chat : Long,
    var boardid : Long,
    val writerid : Long
        )