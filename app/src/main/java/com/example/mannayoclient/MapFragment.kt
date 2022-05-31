package com.example.mannayoclient

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.mannayoclient.databinding.MapFragBinding
import com.example.mannayoclient.mainmenulist.MainStoreActivity
import net.daum.mf.map.api.MapView


class MapFragment : Fragment(R.layout.map_frag) {
    lateinit var binding: MapFragBinding
    lateinit var activity: SecondActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MapFragBinding.bind(view)
        activity = context as SecondActivity
        var mapView = MapView(activity)

        var mapViewContainer = binding.KakaoMapView as ViewGroup

        mapViewContainer.addView(mapView)

        binding.goStore.setOnClickListener() {
            startActivity(Intent(requireContext(), MainStoreActivity::class.java))
        }

    }


}