package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.method.Touch.scrollTo
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.RetrofitClient.service
import com.example.mannayoclient.databinding.IdFragBinding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class IdFragment : Fragment(R.layout.id_frag) {
    lateinit var binding: IdFragBinding
    lateinit var mainActivity: MainActivity
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = IdFragBinding.bind(view)

        //제목 변경
        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("계정 찾기")

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.findNickname -> {
                    binding.editName.text = null
                    binding.editPhone.text = null
                    binding.editPhone.hint = "닉네임을 입력해주세요."
                    binding.editPhone.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                }
                R.id.findPhone -> {
                    binding.editName.text = null
                    binding.editPhone.text = null
                    binding.editPhone.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                    binding.editPhone.inputType = InputType.TYPE_CLASS_PHONE
                }
            }
        }
        /*binding.pwbutton.setOnClickListener {
            mainActivity.onFragmentChange(1)
        }*/
        binding.pwbutton.setOnClickListener {
            findNavController().navigate(R.id.action_idFragment_to_pwFragment)
        }


        binding.imageView2.setOnClickListener {
            if (binding.radioGroup.checkedRadioButtonId == R.id.findNickname) {
                val sendNicknameData = SendNicknameRequestData(
                    realname = binding.editName.text.toString(),
                    nickname = binding.editPhone.text.toString()
                )
                service.getMyAccountByNickname(sendNicknameData)
                    .enqueue(object : Callback<resdata> {
                        override fun onFailure(call: Call<resdata>, t: Throwable) {
                            Toast.makeText(mainActivity, "인터넷 연결을 확인해 주세요!", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(call: Call<resdata>, response: Response<resdata>) {
                            if (response.isSuccessful) {
                                val reqresponse = response.body() as resdata
                                val bundle = Bundle()
                                bundle.putString("email", reqresponse.email)
                                findNavController().navigate(
                                    R.id.action_idFragment_to_idFragment2,
                                    bundle
                                )
                            } else {
                                if(binding.editName.text.isNullOrEmpty()) {
                                    Toast.makeText(mainActivity, "이름을 입력하세요!", Toast.LENGTH_SHORT)
                                        .show()
                                }else if(binding.editPhone.text.isNullOrEmpty()) {
                                    Toast.makeText(mainActivity, "닉네임을 입력하세요!", Toast.LENGTH_SHORT)
                                        .show()
                                }else {
                                    Toast.makeText(mainActivity, "이름,닉네임과 일치하는 이메일이 없습니다!", Toast.LENGTH_SHORT)
                                        .show()
                                }

                            }
                        }
                    })
            }else {
                val sendPhoneNumberData = SendPhoneNumberRequestData(
                    realname = binding.editName.text.toString(),
                    phoneNumber = binding.editPhone.text.toString()
                )
                service.getMyAccountByPhoneNumber(sendPhoneNumberData)
                    .enqueue(object : Callback<resdata> {
                        override fun onFailure(call: Call<resdata>, t: Throwable) {
                            Toast.makeText(mainActivity, "인터넷 연결을 확인해 주세요!", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(call: Call<resdata>, response: Response<resdata>) {
                            if (response.isSuccessful) {
                                val reqresponse = response.body() as resdata
                                val bundle = Bundle()
                                bundle.putString("email", reqresponse.email)
                                findNavController().navigate(
                                    R.id.action_idFragment_to_idFragment2,
                                    bundle
                                )
                            } else {
                                Toast.makeText(mainActivity, "이름,핸드폰번호와 일치하는 이메일이 없습니다!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    })
            }
        }
    }

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