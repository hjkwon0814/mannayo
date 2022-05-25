package com.example.mannayoclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mannayoclient.databinding.ActivityReviewWriteBinding

class ReviewWriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityReviewWriteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}