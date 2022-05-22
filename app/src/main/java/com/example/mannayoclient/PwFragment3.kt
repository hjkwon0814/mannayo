package com.example.mannayoclient

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.ProfileFragBinding
import com.example.mannayoclient.databinding.Pw3FragBinding

class PwFragment3 : Fragment(R.layout.pw3_frag) {
    lateinit var binding: Pw3FragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = Pw3FragBinding.bind(view)



        binding.loginBt.setOnClickListener {
            findNavController().navigate(R.id.action_pwFragment3_to_loginFragment)
        }


    }
}