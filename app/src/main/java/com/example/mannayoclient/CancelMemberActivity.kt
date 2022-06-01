package com.example.mannayoclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mannayoclient.databinding.ActivityCancelMemberBinding


class CancelMemberActivity : AppCompatActivity() {
    lateinit var binding: ActivityCancelMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityCancelMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reason.setOnClickListener{
            //다이어로그 띄우기
        }
    }
}