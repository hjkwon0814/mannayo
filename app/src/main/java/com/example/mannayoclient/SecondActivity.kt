package com.example.mannayoclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    fun FragmentView() {
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.fragment, ProfileFragment())
        transaction.commit()
    }
}