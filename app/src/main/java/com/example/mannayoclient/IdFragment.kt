package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.IdFragBinding

class IdFragment : Fragment(R.layout.id_frag) {
    lateinit var binding: IdFragBinding
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = IdFragBinding.bind(view)

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {


                R.id.findNickname -> {
                    binding.editPhone.hint = "닉네임을 입력해주세요."
                    binding.editPhone.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                }
                R.id.findPhone -> {
                    binding.editPhone.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                    binding.editPhone.inputType = InputType.TYPE_CLASS_PHONE
                }
            }
        }



        binding.pwbutton.setOnClickListener {
            mainActivity.onFragmentChange(1)
        }


        binding.submit.setOnClickListener {
            findNavController().navigate(R.id.action_idFragment_to_idFragment2)
        }

    }

}