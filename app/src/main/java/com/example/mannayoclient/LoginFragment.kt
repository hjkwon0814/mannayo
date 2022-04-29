package com.example.mannayoclient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.LoginFragBinding

class LoginFragment : Fragment(R.layout.login_frag) {
    lateinit var binding: LoginFragBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LoginFragBinding.bind(view)

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


    }
}