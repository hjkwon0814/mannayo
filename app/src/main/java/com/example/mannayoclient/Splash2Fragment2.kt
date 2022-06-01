package com.example.mannayoclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mannayoclient.categorylist.CategoryActivity
import com.example.mannayoclient.databinding.Splash2Frag1Binding
import com.example.mannayoclient.databinding.Splash2Frag2Binding

class Splash2Fragment2  : Fragment(R.layout.splash2_frag2) {
    lateinit var binding: Splash2Frag2Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = Splash2Frag2Binding.bind(view)


        //main액티비티로 이동
        binding.next.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }


    }
}