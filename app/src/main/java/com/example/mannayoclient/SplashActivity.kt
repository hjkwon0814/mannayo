package com.example.mannayoclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //splash화면 3초간 띄우기
        Handler().postDelayed({
            startActivity(Intent(this, SplashActivity2::class.java))
            finish()
        }, 2000)
    }
}