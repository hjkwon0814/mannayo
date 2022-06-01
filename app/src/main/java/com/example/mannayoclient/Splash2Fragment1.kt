package com.example.mannayoclient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.Splash2Frag1Binding

class Splash2Fragment1 : Fragment(R.layout.splash2_frag1) {
    lateinit var binding: Splash2Frag1Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = Splash2Frag1Binding.bind(view)



        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_splash2Fragment1_to_splash2Fragment2)
        }


    }
}