package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.PwFragBinding

class PwFragment : Fragment(R.layout.pw_frag) {
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = PwFragBinding.bind(view)

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.findEmail -> {binding.editPhone2.hint = "이메일을 입력하세요."
                    binding.editPhone2.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS}
                R.id.findPhone -> {binding.editPhone2.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                    binding.editPhone2.inputType = InputType.TYPE_CLASS_PHONE}


            }
        }

        binding.idbutton.setOnClickListener {
            mainActivity.onFragmentChange(0)
        }

        binding.imageView2.setOnClickListener {
            findNavController().navigate(R.id.action_pwFragment_to_PWFragment2)
        }


    }

}