package com.example.mannayoclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.MypageFragBinding
import com.example.mannayoclient.databinding.Pw3FragBinding

class MypageFragment  : Fragment(R.layout.mypage_frag) {
    lateinit var binding: MypageFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MypageFragBinding.bind(view)

        binding.information.setOnClickListener {
            startActivity(Intent(requireContext(), InformationActivity::class.java))
        }


    }
}