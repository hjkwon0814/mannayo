package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mannayoclient.databinding.Login2FragBinding


class LoginFragment2: Fragment(R.layout.login2_frag) {
    lateinit var binding: Login2FragBinding
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = Login2FragBinding.bind(view)






    }
}