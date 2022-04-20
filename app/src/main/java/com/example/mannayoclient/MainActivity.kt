package com.example.mannayoclient

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.example.mannayoclient.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.GsonBuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()



        binding.button2.setOnClickListener {
            val requestdata = reqdata(
                realname = binding.editTextTextPersonName.text.toString(),
                nickname = binding.editphone.text.toString()
            )

            val service = retrofit.create(mannayoService::class.java)
            service.getMyAccount(requestdata).enqueue(object :Callback<resdata>
            {
                override fun onFailure(call: Call<resdata>, t: Throwable) {
                    println("failed")
                }

                override fun onResponse(call: Call<resdata>, response: Response<resdata>) {
                    if(response.isSuccessful) {
                        println("***" + requestdata.realname + requestdata.nickname + "***")
                        println("success")
                        println(response.body())
                        val reqresponse = response.body() as resdata
                        binding.textView4.text = requestdata.realname + "님의 이메일 아이디는 " + reqresponse.email + " 입니다."
                    }else {
                        println("Response Failed")
                        binding.textView4.text = requestdata.realname
                    }
                }
            })
        }


        binding.button6.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
        }

        binding.radioGroup.setOnCheckedChangeListener {radioGroup, checkedId ->
            when(checkedId) {


                R.id.nickname -> {binding.editphone.hint = "닉네임을 입력해주세요."
                    binding.editphone.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL}
                R.id.phone -> {binding.editphone.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                    binding.editphone.inputType = InputType.TYPE_CLASS_PHONE}

            }
        }
    }
}

public interface  mannayoService {
    @POST("/members/findMyAccountByNickname")
    fun getMyAccount(@Body reqdata: reqdata): Call<resdata>
}
data class reqdata (
    @SerializedName("nickName")
    @Expose
    val nickname: String,
    @SerializedName("realName")
    @Expose
    val realname: String
        )
data class resdata(
    @SerializedName("email")
    @Expose
    val email : String
)