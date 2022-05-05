package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.mannayoclient.databinding.*


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


    //EditText 외에 다른곳을 누르면 키보드 내려가기
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        return true
    }



}









