package com.example.mannayoclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val FLAG_PERM_STORAGE = 99

        ActivityCompat.requestPermissions(this, STORAGE_PERMISSION, FLAG_PERM_STORAGE)
    }

    fun FragmentView() {
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.fragment, ProfileFragment())
        transaction.commit()
    }

    fun FragmentViewFromProfileToMainHome() {
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.fragment, MainHomeFragment())
        transaction.commit()
    }
}