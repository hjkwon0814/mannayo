package com.example.mannayoclient.advertiselist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.AdvertiseFragBinding


class AdvertiseFragment : Fragment(R.layout.advertise_frag) {
    lateinit var binding: AdvertiseFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = AdvertiseFragBinding.bind(view)

        val rv: RecyclerView = binding.rv

        val items = ArrayList<AdvertiseModel>()

        //test 용
        items.add(AdvertiseModel("a"))
        items.add(AdvertiseModel("a"))
        items.add(AdvertiseModel("a"))


        val rvAdapter = AdvertiseRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

        rvAdapter.itemClick = object : AdvertiseRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //findNavController().navigate(R.id.)
            }
        }

        binding.write.setOnClickListener {
            findNavController().navigate(R.id.action_advertiseFragment_to_writeFragment)
        }


    }
}