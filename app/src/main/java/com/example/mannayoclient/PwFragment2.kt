package com.example.mannayoclient

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mannayoclient.databinding.Pw2FragBinding
import androidx.navigation.fragment.findNavController

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

        //제목 변경
        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("계정 찾기")



        binding.idbutton.setOnClickListener {
            findNavController().navigate(R.id.action_pwFragment2_to_idFragment)
        }

        /*binding.idbutton.setOnClickListener {
            mainActivity.onFragmentChange(0)
        }*/


    }

}