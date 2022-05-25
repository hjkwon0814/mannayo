package com.example.mannayoclient.mainmenulist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mannayoclient.databinding.ActivityMainStoreBinding

class MainStoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}