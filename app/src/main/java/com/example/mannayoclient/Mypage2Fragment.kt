package com.example.mannayoclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mannayoclient.databinding.Mypage2FragBinding

class Mypage2Fragment : Fragment(R.layout.mypage2_frag) {
    lateinit var binding: Mypage2FragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = Mypage2FragBinding.bind(view)

        binding.nickChange.setOnClickListener{
            startActivity(Intent(requireContext(), InformationActivity::class.java))
        }

        binding.pwChange.setOnClickListener{
            startActivity(Intent(requireContext(), InformationActivity2::class.java))
        }


    }
}