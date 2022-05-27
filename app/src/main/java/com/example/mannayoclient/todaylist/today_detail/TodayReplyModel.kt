package com.example.mannayoclient.todaylist.today_detail

import android.graphics.Bitmap

data class TodayReplyModel (
        var name: String = "",
        var date: String = "",
        var reply: String = "",
        var image: Bitmap,
        val type: String

)