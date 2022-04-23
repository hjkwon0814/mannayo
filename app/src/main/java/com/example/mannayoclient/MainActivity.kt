package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*



class IdFragment : Fragment(R.layout.id_frag){
    lateinit var binding2: IdFragBinding
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding2 = IdFragBinding.bind(view)

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
            mainActivity.onFragmentChange(1)
        }


        binding2.submit.setOnClickListener {
            findNavController().navigate(R.id.action_idFragment_to_idFragment2)
        }

        }

      }



class PwFragment : Fragment(R.layout.pw_frag) {
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
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
            mainActivity.onFragmentChange(0)
        }

        binding3.submit.setOnClickListener {
            findNavController().navigate(R.id.action_pwFragment_to_PWFragment2)
        }


    }

}

class IdFragment2 : Fragment(R.layout.id2_frag) {
    lateinit var binding6: Id2FragBinding
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding6 = Id2FragBinding.bind(view)

        binding6.pwbutton.setOnClickListener {
            mainActivity.onFragmentChange(1)
        }

    }
}

class PWFragment2 : Fragment(R.layout.pw2_frag) {
    lateinit var binding4: Pw2FragBinding
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding4 = Pw2FragBinding.bind(view)

        binding4.idbutton.setOnClickListener {
            mainActivity.onFragmentChange(0)
        }
    }

}

class LoginFragment : Fragment(R.layout.login_frag)  {
    lateinit var binding5: LoginFragBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding5 = LoginFragBinding.bind(view)

        binding5.submit.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_idFragment)
        }

    }

}


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