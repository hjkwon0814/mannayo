package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.Id2FragBinding

class IdFragment2 : Fragment(R.layout.id2_frag) {
    lateinit var binding: Id2FragBinding
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = Id2FragBinding.bind(view)

        //제목 변경
        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("계정 찾기")

        binding.loginEmail.text = arguments?.getString("email")

        binding.pwbutton.setOnClickListener {
            findNavController().navigate(R.id.action_idFragment2_to_pwFragment)
        }

        binding.joinSumbit.setOnClickListener {
            findNavController().navigate(R.id.action_idFragment2_to_loginFragment)
        }

    }
}