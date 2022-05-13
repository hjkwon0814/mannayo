package com.example.mannayoclient

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.InputType
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.ActivityMainBinding
import com.example.mannayoclient.databinding.LoginFragBinding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment(R.layout.login_frag) {
    lateinit var binding: LoginFragBinding
    lateinit var mainActivity: MainActivity
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = context as MainActivity

        binding = LoginFragBinding.bind(view)

        val BASE_URL = "http://192.168.0.2:8080"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: mannayoService = retrofit.create(mannayoService::class.java)

        sharedPreferences = mainActivity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("로그인")


        if(!sharedPreferences.getString("email","").toString().isNullOrEmpty() && !sharedPreferences.getString("password","").toString().isNullOrEmpty()) {
            autologin(service, sharedPreferences.getString("email","").toString() ,sharedPreferences.getString("password","").toString())
        }

        //아이디/비밀번호 찾기
        binding.idPw.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_idFragment)
        }
        //회원가입 >
        binding.joinButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }
        binding.joinButton2.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }
        binding.view4.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }

        binding.loginSubmit.setOnClickListener{
            login(service)

        }

    }

    private fun login(service : mannayoService) {
        service.signIn(binding.loginId.text.toString(), binding.loginPw.text.toString())
            .enqueue(object : Callback<ReceiveLoginOK> {
                override fun onResponse(
                    call: Call<ReceiveLoginOK>,
                    response: Response<ReceiveLoginOK>
                ) {
                    val receive = response.body() as ReceiveLoginOK
                    println(receive.code)
                    println(receive.response)
                    println(receive.success)
                    println(receive.data)
                    if(response.isSuccessful && receive.success) {
                        if(binding.loginState.isChecked) {
                            editor.putString("email", binding.loginId.text.toString())
                            editor.putString("password", binding.loginPw.text.toString())
                            editor.commit()
                        }

                        if(receive.nickname.equals("null")) {
                            findNavController().navigate(R.id.action_loginFragment_to_profileFragment2)
                        }else {
                            findNavController().navigate(R.id.action_loginFragment_to_mainHomeFragment2)
                        }
                        Toast.makeText(mainActivity, "로그인 성공!!", Toast.LENGTH_SHORT)
                            .show()
                    }else {
                        Toast.makeText(mainActivity, receive.data, Toast.LENGTH_SHORT)
                            .show()

                    }
                }

                override fun onFailure(call: Call<ReceiveLoginOK>, t: Throwable) {
                    Toast.makeText(mainActivity, "서버연결 실패!!", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }


    private fun autologin(service : mannayoService, email: String, password: String) {
        service.signIn(email, password)
            .enqueue(object : Callback<ReceiveLoginOK> {
                override fun onResponse(
                    call: Call<ReceiveLoginOK>,
                    response: Response<ReceiveLoginOK>
                ) {
                    val receive = response.body() as ReceiveLoginOK
                    println(receive.code)
                    println(receive.response)
                    println(receive.success)
                    println(receive.data)
                    if(response.isSuccessful && receive.success) {
                        if(binding.loginState.isChecked) {
                            editor.putString("email", binding.loginId.text.toString())
                            editor.putString("password", binding.loginPw.text.toString())
                            editor.commit()
                        }
                        if(receive.nickname.equals("null")) {
                            findNavController().navigate(R.id.action_loginFragment_to_profileFragment2)
                        }else {
                            findNavController().navigate(R.id.action_loginFragment_to_mainHomeFragment2)
                        }
                        Toast.makeText(mainActivity, "자동 로그인 성공!!", Toast.LENGTH_SHORT)
                            .show()
                    }else {
                        Toast.makeText(mainActivity, receive.data, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ReceiveLoginOK>, t: Throwable) {
                    Toast.makeText(mainActivity, "서버연결 실패!!", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }
}

data class SendLoginData (
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("password")
    @Expose
    val password: String
)

data class ReceiveLoginOK (
    @SerializedName("success")
    @Expose
    val success : Boolean,

    @SerializedName("code")
    @Expose
    val code : Int,

    @SerializedName("msg")
    @Expose
    val response: String,

    @SerializedName("data")
    @Expose
    val data : String,

    @SerializedName("nickname")
    @Expose
    val nickname : String
    )