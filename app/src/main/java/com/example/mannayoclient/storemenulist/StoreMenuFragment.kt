package com.example.mannayoclient.storemenulist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.StoremenuFragBinding


class StoreMenuFragment : Fragment(R.layout.storemenu_frag){
    lateinit var binding: StoremenuFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = StoremenuFragBinding.bind(view)

        //val rv : RecyclerView = binding.recyclerView

        val items = ArrayList<StoreMenuModel>()

        //test 용
        items.add(StoreMenuModel("a"))
        items.add(StoreMenuModel("b"))
        items.add(StoreMenuModel("c"))
        items.add(StoreMenuModel("a"))


        val rvAdapter = StoreMenuRVAdapter(items)
        //rv.adapter = rvAdapter

        //rv.layoutManager = LinearLayoutManager(requireContext())




    }
}