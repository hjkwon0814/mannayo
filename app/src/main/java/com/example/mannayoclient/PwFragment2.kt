package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mannayoclient.databinding.Pw2FragBinding

class PwFragment2 : Fragment(R.layout.pw2_frag) {
    lateinit var binding: Pw2FragBinding
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = Pw2FragBinding.bind(view)

        binding.idbutton.setOnClickListener {
            mainActivity.onFragmentChange(0)
        }


    }

}