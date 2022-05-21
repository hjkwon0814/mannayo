package com.example.mannayoclient.storereviewlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.StorereviewFragBinding

class StoreReviewFragment : Fragment(R.layout.storereview_frag){
    lateinit var binding: StorereviewFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = StorereviewFragBinding.bind(view)

        val rv : RecyclerView = binding.recyclerView

        val items = ArrayList<StoreReviewModel>()

        //test 용
        items.add(StoreReviewModel("a"))
        items.add(StoreReviewModel("a"))
        items.add(StoreReviewModel("a"))




        val rvAdapter = StoreReviewRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())




    }
}