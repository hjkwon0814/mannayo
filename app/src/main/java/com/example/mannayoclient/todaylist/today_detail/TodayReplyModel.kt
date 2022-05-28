package com.example.mannayoclient.todaylist.today_detail

import android.graphics.Bitmap

data class TodayReplyModel (
        val type: Int,
        val name: String?,
        val date: String?,
        val reply: String?
        //val image: Bitmap,
) {
        companion object {
                const val reply1 = 0
                const val reply2 = 1
        }
}