package com.example.mannayoclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mannayoclient.databinding.MapFragBinding
import com.example.mannayoclient.mainmenulist.MainStoreActivity

class MapFragment : Fragment(R.layout.map_frag) {
    lateinit var binding: MapFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MapFragBinding.bind(view)


        binding.goStore.setOnClickListener() {
            startActivity(Intent(requireContext(), MainStoreActivity::class.java))
        }


    }
}