package com.example.mannayoclient

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.ActivityMainBinding
import com.example.mannayoclient.databinding.IdFragBinding
import com.example.mannayoclient.databinding.PwFragBinding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*
class IdFragment : Fragment(R.layout.id_frag){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding2 = IdFragBinding.bind(view)

        binding2.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {


                R.id.findEmail -> {
                    binding2.editPhone.hint = "닉네임을 입력해주세요."
                    binding2.editPhone.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                }
                R.id.findPhone -> {
                    binding2.editPhone.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                    binding2.editPhone.inputType = InputType.TYPE_CLASS_PHONE
                }
            }
        }

        binding2.pwbutton.setOnClickListener {
            findNavController().navigate(R.id.action_idFragment_to_pwFragment)
        }
        binding2.submit.setOnClickListener {
            findNavController().navigate(R.id.action_idFragment_to_idFragment2)
        }

      }

    }

class PwFragment : Fragment(R.layout.pw_frag) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding3 = PwFragBinding.bind(view)

        binding3.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.findEmail -> {binding3.editPhone2.hint = "이메일을 입력하세요."
                    binding3.editPhone2.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS}
                R.id.findPhone -> {binding3.editPhone2.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                    binding3.editPhone2.inputType = InputType.TYPE_CLASS_PHONE}


            }
        }

        binding3.idbutton.setOnClickListener {
            findNavController().navigate(R.id.action_pwFragment_to_idFragment)
        }

    }

}
class IdFragment2 : Fragment(R.layout.id2_frag)




class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)



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