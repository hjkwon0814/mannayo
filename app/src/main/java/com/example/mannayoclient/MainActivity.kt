package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    fun onFragmentChange(index:Int) {
        when(index){
            0->supportFragmentManager.commit{
                setReorderingAllowed(true)
                replace(R.id.fragment, IdFragment::class.java, null)

            }
            1->supportFragmentManager.commit{
                setReorderingAllowed(true)
                replace(R.id.fragment, PwFragment::class.java, null)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}









