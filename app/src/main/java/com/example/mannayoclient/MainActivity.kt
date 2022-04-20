package com.example.mannayoclient

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.ActivityMainBinding
import com.example.mannayoclient.databinding.IdFragBinding
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
class IdFragment : Fragment(R.layout.id_frag){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding2 = IdFragBinding.bind(view)
        binding2.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {


                R.id.nickname -> {
                    binding2.editphone.hint = "닉네임을 입력해주세요."
                    binding2.editphone.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                }
                R.id.phone -> {
                    binding2.editphone.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                    binding2.editphone.inputType = InputType.TYPE_CLASS_PHONE
                }
            }
        }

        binding2.pwbutton.setOnClickListener {
            findNavController().navigate(R.id.action_idFragment_to_pwFragment)
        }

    }
}
class PwFragment : Fragment(R.layout.pw_frag) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding3 = IdFragBinding.bind(view)
        binding3.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.email -> {binding3.editphone.hint = "이메일을 입력하세요."
                    binding3.editphone.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS}
                R.id.phone -> {binding3.editphone.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                    binding3.editphone.inputType = InputType.TYPE_CLASS_PHONE}


            }
        }

        binding3.idbutton.setOnClickListener {
            findNavController().navigate(R.id.action_idFragment_to_pwFragment)
        }

    }

}


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
            val sendNicknameData = SendNicknameRequestData(
                realname = binding.editTextTextPersonName.text.toString(),
                nickname = binding.editphone.text.toString()
            )

            val sendPhoneNumberData = SendPhoneNumberRequestData(
                realname = binding.editTextTextPersonName.text.toString(),
                phoneNumber = binding.editphone.text.toString()
            )

            val service = retrofit.create(mannayoService::class.java)
            if (binding.radioGroup.checkedRadioButtonId == R.id.nickname) {
                service.getMyAccountByNickname(sendNicknameData).enqueue(object : Callback<resdata> {
                    override fun onFailure(call: Call<resdata>, t: Throwable) {
                        binding.textView4.text = "인터넷 연결을 확인해 주세요!!"
                    }

                    override fun onResponse(call: Call<resdata>, response: Response<resdata>) {
                        if (response.isSuccessful) {
                            val reqresponse = response.body() as resdata
                            binding.textView4.text =
                                sendNicknameData.realname + "님의 이메일 아이디는 " + reqresponse.email + " 입니다."
                        } else {
                            binding.textView4.text = "가입하신 이력이 없습니다."
                        }
                    }
                })
            }else if(binding.radioGroup.checkedRadioButtonId == R.id.phone) {
                service.getMyAccountByPhoneNumber(sendPhoneNumberData).enqueue(object : Callback<resdata> {
                    override fun onFailure(call: Call<resdata>, t: Throwable) {
                        binding.textView4.text = "인터넷 연결을 확인해 주세요!!"
                    }

                    override fun onResponse(call: Call<resdata>, response: Response<resdata>) {
                        if (response.isSuccessful) {
                            val reqresponse = response.body() as resdata
                            binding.textView4.text =
                                sendPhoneNumberData.realname + "님의 이메일 아이디는 " + reqresponse.email + " 입니다."
                        } else {
                            binding.textView4.text = "가입하신 이력이 없습니다."
                        }
                    }
                })
            }
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
    fun getMyAccountByNickname(@Body reqdata: SendNicknameRequestData): Call<resdata>

    @POST("/members/findMyAccountByPhoneNumber")
    fun getMyAccountByPhoneNumber(@Body reqdata: SendPhoneNumberRequestData): Call<resdata>
}
data class SendNicknameRequestData (
    @SerializedName("nickName")
    @Expose
    val nickname: String,
    @SerializedName("realName")
    @Expose
    val realname: String
        )
data class SendPhoneNumberRequestData (
    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String,
    @SerializedName("realName")
    @Expose
    val realname : String
        )
data class resdata(
    @SerializedName("email")
    @Expose
    val email : String
)