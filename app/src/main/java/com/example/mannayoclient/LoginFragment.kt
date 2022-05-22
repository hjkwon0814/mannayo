package com.example.mannayoclient

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.LoginFragBinding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment(R.layout.login_frag) {
    lateinit var binding: LoginFragBinding
    lateinit var mainActivity: MainActivity
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = context as MainActivity

        binding = LoginFragBinding.bind(view)




        sharedPreferences = mainActivity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("로그인")

        println(sharedPreferences.getString("email","").toString())
        if(!sharedPreferences.getString("email",null).toString().isNullOrEmpty() && !sharedPreferences.getString("password",null).toString().isNullOrEmpty()) {
            println("y?")
            autologin(retrofitService.service, sharedPreferences.getString("email","").toString() ,sharedPreferences.getString("password","").toString())
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
            login(retrofitService.service)

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
                            editor.putString("id",receive.id.toString())
                            editor.putString("nickname", receive.nickname)
                            editor.commit()
                        }
                        mainActivity.onActivityChange()


                        if(receive.nickname.equals("null")) {
                           // findNavController().navigate(R.id.action_loginFragment_to_profileFragment2)
                        }else {
                            mainActivity.onActivityChange()
                        }

                        Toast.makeText(mainActivity, "로그인 성공!!", Toast.LENGTH_SHORT)
                            .show()
                    }else {

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
                    if(response.isSuccessful && receive.success) {
                            editor.putString("id", receive.id.toString())
                            editor.putString("nickname", receive.nickname)
                            editor.commit()

                            //findNavController().navigate(R.id.action_loginFragment_to_profileFragment2)
                        }else {

                            mainActivity.onActivityChange()
                        Toast.makeText(mainActivity, "자동 로그인 성공!!", Toast.LENGTH_SHORT)
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
    val nickname : String,

    @SerializedName("id")
    @Expose
    val id : Long
    )