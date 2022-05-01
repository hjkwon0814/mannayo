package com.example.mannayoclient

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.JoinFragBinding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.time.LocalDate
import java.util.*

class JoinFragment : Fragment(R.layout.join_frag) {
    lateinit var binding: JoinFragBinding
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = JoinFragBinding.bind(view)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(mannayoService::class.java)

        binding.editTextDate.setOnClickListener {
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date : Int = today.get(Calendar.DATE)
        }



        //제목 변경
        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("회원가입")

        binding.joinSubmit.setOnClickListener {
            val request = signUpRequest(
                email = binding.editTextTextEmailAddress.text.toString(),
                password = binding.editTextTextPassword.text.toString(),
                realname = binding.editTextTextPersonName.text.toString(),
                birth = binding.editTextDate.text.toString() + "-" + binding.editTextDate2.text.toString() + "-" + binding.editTextDate3.text.toString(),
                phoneNumber = binding.editTextNumber.text.toString(),
                image = "",
                accountStatus = "OPEN",
                accountTypeEnum = "MEMBER",
                loginTypeEnum = "EMAIL"
            )
            if(binding.editTextTextPassword.text.toString() != binding.editTextTextPassword2.text.toString()) {
                Toast.makeText(mainActivity, "비밀번호가 다릅니다!!!", Toast.LENGTH_SHORT)
                    .show()
            }else if(!binding.editTextTextEmailAddress.text.isNullOrEmpty() && !binding.editTextTextPassword.text.isNullOrEmpty()
                && !binding.editTextTextPassword2.text.isNullOrEmpty() && !binding.editTextTextPersonName.text.isNullOrEmpty()
                && !binding.editTextDate.text.isNullOrEmpty() && !binding.editTextDate2.text.isNullOrEmpty() && !binding.editTextDate3.text.isNullOrEmpty()
                && !binding.editTextNumber.text.isNullOrEmpty()) {
                service.signUp(request).enqueue(object : Callback<resSignUpData> {
                    override fun onResponse(call: Call<resSignUpData>, response: Response<resSignUpData>) {
                        val reqresponse = response.body() as resSignUpData

                        if (response.isSuccessful && reqresponse.response == "성공") {
                            findNavController().navigate(R.id.action_joinFragment_to_join2Fragment)
                        } else {

                        }
                    }

                    override fun onFailure(call: Call<resSignUpData>, t: Throwable) {
                        Toast.makeText(mainActivity, "인터넷 연결을 확인해 주세요!", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }else if(binding.editTextTextEmailAddress.text.isNullOrEmpty()) {
                Toast.makeText(mainActivity, "이메일을 입력하세요!", Toast.LENGTH_SHORT)
                    .show()
            }else if(binding.editTextTextPassword.text.isNullOrEmpty()) {
                Toast.makeText(mainActivity, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT)
                    .show()
            }else if(binding.editTextTextPersonName.text.isNullOrEmpty()) {
                Toast.makeText(mainActivity, "이름을 입력하세요!", Toast.LENGTH_SHORT)
                    .show()
            }else if(binding.editTextDate.text.isNullOrEmpty()) {
                Toast.makeText(mainActivity, "년도를 입력하세요!", Toast.LENGTH_SHORT)
                    .show()
            }else if(binding.editTextDate2.text.isNullOrEmpty()) {
                Toast.makeText(mainActivity, "월을 입력하세요!", Toast.LENGTH_SHORT)
                    .show()
            }else if(binding.editTextDate3.text.isNullOrEmpty()) {
                Toast.makeText(mainActivity, "일을 입력하세요!", Toast.LENGTH_SHORT)
                    .show()
            }else if(binding.editTextNumber.text.isNullOrEmpty()) {
                Toast.makeText(mainActivity, "휴대전화번호 입력하세요!", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }


}

data class signUpRequest(
    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("password")
    @Expose
    val password: String,

    @SerializedName("realname")
    @Expose
    val realname: String,

    @SerializedName("birth")
    @Expose
    val birth : String,

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String,

    @SerializedName("loginTypeEnum")
    @Expose
    val loginTypeEnum : String,

    @SerializedName("imageAddress")
    @Expose
    val image : String,

    @SerializedName("accountTypeEnum")
    @Expose
    val accountTypeEnum : String,

    @SerializedName("accountStatus")
    @Expose
    val accountStatus : String
)

data class resSignUpData(
    @SerializedName("success")
    @Expose
    val success : Boolean,

    @SerializedName("code")
    @Expose
    val code : Int,

    @SerializedName("msg")
    @Expose
    val response: String


)
