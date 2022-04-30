package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.JoinFragBinding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST
import java.time.LocalDate

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

        //제목 변경
        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("회원가입")

        binding.joinSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_joinFragment_to_join2Fragment)
        }
    }
}

public interface mannyoService {
    @POST("/signup")
    fun signUp(@Body reqdata : signUpRequest);
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
    val birth : LocalDate,

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String,

    @SerializedName("nickname")
    @Expose
    val nickname: String,

    @SerializedName("reportcount")
    @Expose
    val reportCount : Int,

    @SerializedName("loginTypeEnum")
    @Expose
    val loginTypeEnum : String,

    @SerializedName("image")
    @Expose
    val image : String,

    @SerializedName("id")
    @Expose
    val id : Int,

    @SerializedName("accountTypeEnum")
    @Expose
    val accountTypeEnum : String,

    @SerializedName("accountStatus")
    @Expose
    val accountStatus : String
)

