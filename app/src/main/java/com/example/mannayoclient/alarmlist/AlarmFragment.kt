package com.example.mannayoclient.alarmlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.AlarmFragBinding

class AlarmFragment : Fragment(R.layout.alarm_frag) {
    lateinit var binding: AlarmFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = AlarmFragBinding.bind(view)

        val rv: RecyclerView = binding.rv

        val items = ArrayList<AlarmModel>()

        //test 용
        items.add(AlarmModel("a", "b", "c"))
        items.add(AlarmModel("a", "b", "c"))
        items.add(AlarmModel("a", "b", "c"))


        val rvAdapter = AlarmRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

        binding.alarmImg.setOnClickListener {
            binding.alarmImg.setImageResource(R.drawable.alarm)
            binding.messageImg.setImageResource(R.drawable.message)
        }

        binding.messageImg.setOnClickListener {
            binding.alarmImg.setImageResource(R.drawable.alarm2)
            binding.messageImg.setImageResource(R.drawable.message2)
        }


    }
}
