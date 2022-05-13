package com.example.mannayoclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.mannayoclient.databinding.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

    fun onActivityChange() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
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

object RetrofitClient {
    // retrofit client 선언

    // BASE_URL을 private const 변수로 저장
    private const val BASE_URL = "192.168.43.198:8080"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: mannayoService = retrofit.create(mannayoService::class.java)
}









