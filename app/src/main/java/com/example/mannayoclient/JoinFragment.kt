package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.JoinFragBinding

class JoinFragment : Fragment(R.layout.join_frag) {
    lateinit var binding: JoinFragBinding
    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = JoinFragBinding.bind(view)

        //제목 변경
        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("회원가입")




        binding.joinSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_joinFragment_to_join2Fragment)
        }

    }
}