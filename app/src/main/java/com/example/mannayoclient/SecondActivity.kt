package com.example.mannayoclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.categorylist.CategoryListFragment
import com.example.mannayoclient.storereviewlist.StoreReviewFragment
import java.util.jar.Manifest

class SecondActivity : AppCompatActivity() {
    private var backPressedTime: Long = 0
    val TIME_INTERVAL: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val FLAG_PERM_STORAGE = 99

        ActivityCompat.requestPermissions(this, STORAGE_PERMISSION, FLAG_PERM_STORAGE)
    }


    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val intervalTime = currentTime - backPressedTime
        if (intervalTime in 0..TIME_INTERVAL) finish()
        else {
            backPressedTime = currentTime
            Toast.makeText(this,"뒤로 가기 버튼을 한번 더 클릭하면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun FragmentView() {
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.fragment, ProfileFragment())
        transaction.commit()
    }

    fun FragmentViewFromProfileToMainHome() {
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.fragment, MainHomeFragment())
        transaction.commit()
    }

    fun FragmentViewFromReviewWriteToStoreReview() {
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.fragment, StoreReviewFragment(), "store")
        transaction.addToBackStack("store")
        transaction.commit()
    }

}