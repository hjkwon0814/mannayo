package com.example.mannayoclient.todaylist.today_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.TodaydetailFragBinding
import com.example.mannayoclient.writelist.WriteModel
import com.example.mannayoclient.writelist.WriteRVAdapter

class TodayDetailFragment : Fragment(R.layout.todaydetail_frag) {
    lateinit var binding: TodaydetailFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = TodaydetailFragBinding.bind(view)

        val rv: RecyclerView = binding.dVoteRecyclerView

        val items = ArrayList<TodayVoteModel>()

        //test 용
        items.add(TodayVoteModel("a"))
        items.add(TodayVoteModel("b"))


        val rvAdapter = TodayVoteRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())



    }
}