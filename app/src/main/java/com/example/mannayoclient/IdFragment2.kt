package com.example.mannayoclient

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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

        binding.pwbutton.setOnClickListener {
            mainActivity.onFragmentChange(1)
        }

    }
}