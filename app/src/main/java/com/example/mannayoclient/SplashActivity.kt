package com.example.mannayoclient

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        sharedPreferences = this.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val genesis = sharedPreferences.getString("Genesis",null)
        if(!genesis.equals("Genesis")) {
            //splash화면 2초간 띄우기
            Handler().postDelayed({
                startActivity(Intent(this, SplashActivity2::class.java))
                finish()
            }, 2000)
        }else {
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }

    }
}