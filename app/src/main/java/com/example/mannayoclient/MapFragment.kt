package com.example.mannayoclient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.MapFragBinding

class MapFragment : Fragment(R.layout.map_frag) {
    lateinit var binding: MapFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MapFragBinding.bind(view)


        binding.goStore.setOnClickListener() {
            findNavController().navigate(R.id.action_mapFragment_to_mainStoreFragment)
        }


    }
}