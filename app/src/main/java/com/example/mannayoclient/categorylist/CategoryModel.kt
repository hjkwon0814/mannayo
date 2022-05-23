package com.example.mannayoclient.categorylist

import android.graphics.Bitmap


class CategoryModel (
    var title: String = "",
    var detail: String = "",
    var open_time: String = "",
    var grade: String = "",
    var image : Bitmap,
    var restaurantId: Long
        )