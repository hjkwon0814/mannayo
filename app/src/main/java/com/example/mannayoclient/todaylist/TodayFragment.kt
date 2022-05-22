package com.example.mannayoclient.todaylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.advertiselist.AdvertiseModel
import com.example.mannayoclient.advertiselist.AdvertiseRVAdapter
import com.example.mannayoclient.databinding.AdvertiseFragBinding
import com.example.mannayoclient.databinding.TodayFragBinding

class TodayFragment  : Fragment(R.layout.today_frag){
    lateinit var binding: TodayFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = TodayFragBinding.bind(view)

        val rv : RecyclerView = binding.rv

        val items = ArrayList<TodayModel>()

        //test 용
        items.add(TodayModel("a"))
        items.add(TodayModel("a"))
        items.add(TodayModel("a"))






        val rvAdapter = TodayRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

        rvAdapter.itemClick = object : TodayRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //findNavController().navigate(R.id.)
            }
        }

        binding.write.setOnClickListener{
            //findNavController().navigate(R.id.)
        }



    }
}