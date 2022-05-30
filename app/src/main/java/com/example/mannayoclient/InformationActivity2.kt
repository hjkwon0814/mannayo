package com.example.mannayoclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mannayoclient.databinding.ActivityInformation2Binding

class InformationActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityInformation2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformation2Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}