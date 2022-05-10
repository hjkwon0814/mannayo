package com.example.mannayoclient

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.LoginFragBinding

class LoginFragment : Fragment(R.layout.login_frag) {
    lateinit var binding: LoginFragBinding
    lateinit var mainActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = context as MainActivity

        binding = LoginFragBinding.bind(view)


        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("로그인")

        //아이디/비밀번호 찾기
        binding.idPw.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_idFragment)
        }

        //회원가입 >
        binding.joinButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }
        binding.joinButton2.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }
        binding.view4.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }


    }
}