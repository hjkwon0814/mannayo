package com.example.mannayoclient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mannayoclient.databinding.WriteFragBinding

class WriteFragment  : Fragment(R.layout.write_frag) {
    lateinit var binding: WriteFragBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding =  WriteFragBinding.bind(view)




    }
}